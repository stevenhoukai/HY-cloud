package com.yyicbc.jobservice.Impl;

import com.alibaba.fastjson.JSONObject;
import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.JsonUtils;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.business.PO.FovaReturnTempPO;
import com.yyicbc.beans.business.VO.FovaReturnTempVO;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.utils.CCustomDate;
import com.yyicbc.beans.utils.HttpUtil;
import com.yyicbc.beans.utils.YyStringUtils;
import com.yyicbc.beans.utils.distribute.IdWorker;
import com.yyicbc.dao.FovaReturnTempDao;
import com.yyicbc.dao.FovaUpdateDataDao;
import com.yyicbc.service.FovaReturnDataService;
import com.yyicbc.service.FovaReturnTempDataService;
import com.yyicbc.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.*;
import java.util.*;

/**
 * @author vic fu
 */

@Service
@Slf4j
public class FovaReturnTempDtServiceImpl implements FovaReturnTempDataService {

    //发送日期
    private String senddate;

    @Resource
    private FovaReturnTempDao fovaReturnTempDao;

    @Resource
    private FovaReturnDataService fovaReturnDataService;

    @Resource
    FovaUpdateDataDao fovaUpdateDataDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    IdWorker idWorker;

    //匯總標志
    String TOTAL_KEY = "DR";

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateFovaDataByTemp(List<FovaReturnTempPO> tempList) throws BusinessException {

        try {

            //1、先从ICBC提供的接口 获取数据
            if (CollectionUtils.isNotEmpty(tempList) && tempList.size() > 0) {

                //对数据进行分组
                Object[] groupMap = groupMapByList(tempList);
                //汇总
                LinkedHashMap totalMap = (LinkedHashMap<String, FovaReturnTempPO>) groupMap[0];
                //明细
                LinkedHashMap<String, List> detailMap = (LinkedHashMap<String, List>) groupMap[1];

                log.info("***T+1数据汇总数:****" + totalMap.size());

                if (totalMap.size() == 0 || detailMap.size() == 0) {
                    log.info("不存在T+1数据为空");
                    return;
                }

                //获取对应的fovaUpdate 数据
                List<FovaUpdateDataPO> fovaUpdateDataList = updateFovaData(totalMap, detailMap);

                //3根据fovaupdate 更新来源发放薪资子表的数据
                log.info("***根据fovaupdate 更新来源发放薪资子表的数据");
                fovaReturnDataService.updateComBByFovaSrcData(fovaUpdateDataList);

                /*//4根据发送日期senddate删除历史数据
                log.info("***据发送日期senddate删除历史数据");
                fovaReturnTempDao.deleteTempBySendate(senddate);


                //5保存已更新成功的数据
                for (FovaReturnTempPO tempPO : tempList) {
                    tempPO.setId(idWorker.nextId());
                }
                fovaReturnTempDao.saveAll(tempList);*/
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException(StatusCode.ERROR, ex.getMessage());
        }

    }

    /**
     * 功能描述:先保存接口数据
     *
     * @param paramMap
     * @return:
     * @Author: vic
     * @Date: 2020/4/24 17:18
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<FovaReturnTempPO> saveTempData(Map paramMap) throws BusinessException {


        Object sendDateParam = paramMap.get("senddate");
        if (sendDateParam == null || sendDateParam.toString().length() == 0) {
            senddate = CCustomDate.getCurDateByDay(-1);
        } else {
            senddate = sendDateParam.toString();
        }

        try {
            //1、先从ICBC提供的接口 获取数据
            List<FovaReturnTempPO> data = getDataByIcbcUrl();

            log.info("***据发送日期senddate删除历史数据");
            fovaReturnTempDao.deleteTempBySendate(senddate);

            log.info("***保存接口返回T+1的数据{}", data.size());
            if (CollectionUtils.isNotEmpty(data)) {
                for (FovaReturnTempPO tempPO : data) {
                    tempPO.setId(idWorker.nextId());
                }

                log.info("**分页批量保存数:{}***", data.size());
//            batchInsert(data);
                int dataSize = data.size();
                int pageSize = 500;
                int m = dataSize % pageSize;
                int pageCount = 0;
                if (m > 0) {
                    pageCount = dataSize / pageSize + 1;
                } else {
                    pageCount = dataSize / pageSize;
                }
                for (int i = 1; i <= pageCount; i++) {
                    List<FovaReturnTempPO> subList = null;
                    if (i == pageCount) {
                        subList = data.subList((i - 1) * pageSize, dataSize);
                    } else {
                        subList = data.subList((i - 1) * pageSize, pageSize * (i));
                    }
                    batchInsert(subList);
                }
            }
            log.info("****结束导入***");
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException(StatusCode.ERROR, ex.getMessage());
        }

    }

    /**
     * 功能描述:批量保存
     *
     * @param data
     * @return:
     * @Author: vic
     * @Date: 2020/4/24 17:26
     */
    private int batchInsert(List<FovaReturnTempPO> data) {

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO fova_return_temp_t(id, row_num,agtsname,batchno,procnum,drcrf,sendate," +
                "trandate,valuedat,tracurr,amount,status,recordf,mediumid,cashexf,custno,accname,agenpsn ) VALUES ");
        for (FovaReturnTempPO po : data) {
            sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
        }
        String sql = sb.toString().substring(0, sb.length() - 1);
        Query query = entityManager.createNativeQuery(sql);
        int paramIndex = 0;
        for (FovaReturnTempPO po : data) {
            query.setParameter(++paramIndex, po.getId());
            query.setParameter(++paramIndex, po.getRow_num());
            query.setParameter(++paramIndex, po.getAgtsname());
            query.setParameter(++paramIndex, po.getBatchno());
            query.setParameter(++paramIndex, po.getProcnum());
            query.setParameter(++paramIndex, po.getDrcrf());
            query.setParameter(++paramIndex, po.getSendate());
            query.setParameter(++paramIndex, po.getTrandate());
            query.setParameter(++paramIndex, po.getValuedat());
            query.setParameter(++paramIndex, po.getTracurr());
            query.setParameter(++paramIndex, po.getAmount());
            query.setParameter(++paramIndex, po.getStatus());
            query.setParameter(++paramIndex, po.getRecordf());
            query.setParameter(++paramIndex, po.getMediumid());
            query.setParameter(++paramIndex, po.getCashexf());
            query.setParameter(++paramIndex, po.getCustno());
            query.setParameter(++paramIndex, po.getAccname());
            query.setParameter(++paramIndex, po.getAgenpsn());
        }
        return query.executeUpdate();
    }

    /**
     * 根据序号进行分组7
     *
     * @param fovaDataList
     * @return
     */
    private Map groupSernum(List fovaDataList) {

        Map sernumMap = new HashMap();
        for (Object obj : fovaDataList) {
            FovaUpdateDataPO fovaDataPo = (FovaUpdateDataPO) obj;
            if (StringUtils.isNotBlank(fovaDataPo.getSerNum())) {
                sernumMap.put(Integer.valueOf(fovaDataPo.getSerNum()), fovaDataPo);
            }
        }
        return sernumMap;

    }

    /**
     * 更新FovaUpdate
     *
     * @return
     */
    private List<FovaUpdateDataPO> updateFovaData(LinkedHashMap totalMap, LinkedHashMap<String, List> detailMap) throws Exception {

        log.info("***开始更新 fovadata****");
        //需要更新的fovaDataList
        List fovaUpdateDataList = new ArrayList<FovaUpdateDataPO>();

        //需要更新汇总fovadata
        List totalFovaDataList = new ArrayList<FovaUpdateDataPO>();


        for (String key : (Set<String>) detailMap.keySet()) {
            List<FovaReturnTempPO> list = detailMap.get(key);
            log.info("***开始更新fovadata****:子记录{}", list.size());
            if (totalMap.get(key) == null) {
                throw new Exception(key + "明细获取不到对应汇总记录!");
            }
            FovaReturnTempPO totalPO = (FovaReturnTempPO) totalMap.get(key);
            //先从发放薪资表查询出fovadata对应汇总记录，并且更新状态
            List<FovaUpdateDataPO> comTotalList = fovaUpdateDataDao.findTotalDataByCom(totalPO.getAgenpsn(),
                    totalPO.getAmount(), YyStringUtils.repleacDate(totalPO.getSendate()));
            log.info("***开始更新fovadata****:获取发放薪资数据");
            //对应的合计VO
            FovaUpdateDataPO totalFovaDataPO = null;
            if (CollectionUtils.isEmpty(comTotalList) || comTotalList.size() == 0) {
                //如果未找到，则查询用户导入的数据
//                log.info("***开始更新fovadata****:获取用户导入数据金额：{}状态：{}",totalPO.getAmount(),totalPO.getStatus());
                List<FovaUpdateDataPO> userTotalList = fovaUpdateDataDao.findTotalDataByUser(totalPO.getAgenpsn(),
                        totalPO.getAmount().toString(), totalPO.getSendate());
                log.info("***开始更新fovadata****:获取用户导入数据");
                if (CollectionUtils.isEmpty(userTotalList) || userTotalList.size() == 0) {
                    log.info("***开始更新fovadata****:获取用户导入数据null");
                } else {
                    log.info("***开始更新fovadata****:获取用户导入数据{}", userTotalList.size());
                    if (userTotalList.size() > 1) {
                        throw new Exception("客户导入:" + key + "获取到多条FovaData汇总记录，数据异常!");
                    }
                    totalFovaDataPO = userTotalList.get(0);
                }

            } else {
                log.info("***开始更新fovadata****:发放薪资:{}", comTotalList.size());
                if (comTotalList.size() > 1) {
                    throw new Exception("发放薪资：" + key + "获取到多条FovaData汇总记录，数据异常!");
                }
                totalFovaDataPO = comTotalList.get(0);
            }

            if (totalFovaDataPO != null) {
                //添加到需要更新的汇总集合
                log.info("***开始更新fovadata****:获取用户导入数据添加到需要更新的汇总集合：状态{}", totalPO.getStatus());
                totalFovaDataPO.setStatus(YyStringUtils.addZeroByStatus(totalPO.getStatus()));
                totalFovaDataList.add(totalFovaDataPO);
            } else {
                log.info("***开始更新fovadata****:{}获取不到FovaData对应汇总记录,continue!", key);
//                throw new Exception(key + "获取不到FovaData对应汇总记录!");
                continue;
            }
            //根据汇总数据找出对应updatedata 上传文件名称,然后查询出子表记录，根据序号进行对比更新
            List compareList = fovaUpdateDataDao.findDetailDataByUserImportId(totalFovaDataPO.getUserImportId());
            if (CollectionUtils.isEmpty(compareList)) {
                compareList = new ArrayList();
            }
            if (list.size() != compareList.size()) {
                throw new Exception(key + "接口返回明细数量[" + list.size() + "],查询结果fovadata数量:[" + compareList.size() + "]不匹配!");
            }
            log.info("***开始更新fovadata****:明细对比Fovadata明细数量相等");
            //序号进行分组
            Map compareMap = groupSernum(compareList);
            log.info("***开始更新fovadata****:Fovadata按序号进行分组");
            if (compareMap.size() != compareList.size()) {
                throw new Exception(key + "对应fovadata序号存在重复!");
            }
            //根据序号进行更新
            for (FovaReturnTempPO detailVO : list) {
                //序号
                Integer procnum = Integer.valueOf(detailVO.getProcnum());
                if (compareMap.containsKey(procnum)) {
                    //更新对应状态
                    FovaUpdateDataPO fovaUpdateDataPO = (FovaUpdateDataPO) compareMap.get(procnum);
                    fovaUpdateDataPO.setStatus(YyStringUtils.addZeroByStatus(detailVO.getStatus()));
                    fovaUpdateDataList.add(fovaUpdateDataPO);

                } else {
                    log.error(key + "对应fovadata序号[" + procnum + "]不存在!");
                    throw new Exception(key + "对应fovadata序号[" + procnum + "]不存在!");
                }
            }
            log.info("***开始更新fovadata****:明细对比Fovadata明细序号匹配成功!");
        }

        //批量更新汇总数据
        if (CollectionUtils.isNotEmpty(totalFovaDataList)) {
            fovaUpdateDataDao.saveAll(totalFovaDataList);
            log.info("***更新汇总 fovadata****:{}条", totalFovaDataList.size());
        }

        if (CollectionUtils.isNotEmpty(fovaUpdateDataList)) {

            //2、批量更新fova data 明细数据
            fovaUpdateDataDao.saveAll(fovaUpdateDataList);
            log.info("***更新明细 fovadata****:{}条", fovaUpdateDataList.size());
        }

        log.info("***结束更新 fovadata****");

        return fovaUpdateDataList;
    }

    /**
     * 得到匯總數據,
     * 標志為DR ，并且發送日期和交易日期不相同的匯總數據
     * 0 = 汇总  ，1 = 明细
     *
     * @param detailList
     * @return
     */
    private Object[] groupMapByList(List<FovaReturnTempPO> detailList) {

        Object[] rtnMaps = new Object[2];
        //汇总
        LinkedHashMap totalMap = new LinkedHashMap<String, FovaReturnTempPO>();
        //明细
        LinkedHashMap detailMap = new LinkedHashMap<String, List>();

        for (FovaReturnTempPO tempPO : detailList) {

            if (StringUtils.isNotBlank(tempPO.getSendate())
                    && StringUtils.isNotBlank(tempPO.getTrandate())
                    && (!tempPO.getSendate().equals(tempPO.getTrandate()))) {
                String batchno = tempPO.getBatchno();

                //汇总分组
                if (StringUtils.isNotBlank(tempPO.getDrcrf()) && tempPO.getDrcrf().equals(TOTAL_KEY)) {
                    totalMap.put(batchno, tempPO);
                } else { //明细分组
                    List list = new ArrayList();
                    if (detailMap.containsKey(batchno)) {
                        list = (List) detailMap.get(batchno);
                        list.add(tempPO);
                    } else {
                        list.add(tempPO);
                    }
                    detailMap.put(batchno, list);
                }
            }
        }

        rtnMaps[0] = totalMap;
        rtnMaps[1] = detailMap;
        return rtnMaps;
    }

    private String getIcbcUrlT1() {
        return "";
    }

    /**
     * 调用接口获取数据
     *
     * @return
     */
    private List<FovaReturnTempPO> getDataByIcbcUrl() throws Exception {
        return null;
    }

    public static String readFileByBytes(String fileName) {

        FileInputStream fin = null;
        InputStreamReader reader = null;
        StringBuffer str = new StringBuffer();
        try {
            fin = new FileInputStream(fileName);
            reader = new InputStreamReader(fin);
            BufferedReader buffReader = new BufferedReader(reader);
            String strTmp = "";
            while ((strTmp = buffReader.readLine()) != null) {
                str.append(strTmp);
            }
            buffReader.close();

        } catch (Exception e) {
            e.printStackTrace();
            log.error("***读取本地文件路径:{}异常:{}" + fileName, e.getMessage());
        } finally {

            try {
                if (fin != null) {
                    fin.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str.toString();
    }
}
