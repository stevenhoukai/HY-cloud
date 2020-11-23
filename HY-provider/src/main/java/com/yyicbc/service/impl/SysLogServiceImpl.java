package com.yyicbc.service.impl;

import com.yyicbc.Exception.BusinessException;
import com.yyicbc.beans.*;
import com.yyicbc.beans.logmanager.SysLogVO;
import com.yyicbc.beans.querycondition.LogQueryVO;
import com.yyicbc.component.pagehelper.JpaPageHelper;
import com.yyicbc.component.pagehelper.PageInfo;
import com.yyicbc.dao.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.*;


/***
 * @author stv
 * 用户管理接口实现类，切面事务管理
 */
@Service
public class SysLogServiceImpl {

    @Autowired
    SysLogDao sysLogDao;

    @Value("${commons.page.size}")
    private Integer Common_page_size;

    public void addNewRequiredSysLog(String uri,String userInfo,StringBuilder params){
        if(StringUtils.isBlank(userInfo)){
            return;
        }
        SysLogVO sysLogVO = new SysLogVO();
        String[] infoArrays = userInfo.split("&");
        String userCode = infoArrays[0];
        String userName = infoArrays[1];
        //String corpCode = infoArrays[2];
        Set<String> modules = ModuleMap.modulesMap.keySet();
        Iterator<String> modulesIt = modules.iterator();
        while(modulesIt.hasNext()){
            String module = modulesIt.next().toLowerCase();
            if(uri.toLowerCase().startsWith(module)){
                sysLogVO.setModuleName(ModuleMap.modulesMap.get(module));
                sysLogVO.setHandleUsercode(userCode);
                sysLogVO.setHandleUsername(userName);
                sysLogVO.setHandleMemo(params.toString());
                if(uri.contains("/add")||uri.contains("/addform")){
                    sysLogVO.setHandleType(0);
                }else if(uri.contains("/update")||uri.contains("/updateform")){
                    sysLogVO.setHandleType(1);
                }else if(uri.contains("/delete")||uri.contains("/deleteform")){
                    sysLogVO.setHandleType(2);
                }
                if(sysLogVO.getHandleType()!=null){
                    sysLogDao.save(sysLogVO);
                }
            }
        }
    }



    public RetData all(LogQueryVO vo) throws BusinessException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date begindate = new Date(Long.parseLong(vo.getHandleBeginDate()));
        String strbegintime = format.format(begindate).substring(0,10)+" 00:00:00";
        Date enddate = new Date(Long.parseLong(vo.getHandleEndDate()));
        String strendtime = format.format(enddate).substring(0,10)+" 23:59:59";
        Integer page = vo.getPage();
        RetData retData = new RetData();
        RetResult retResult = new RetResult();
        try {
            List<SysLogVO> allList = sysLogDao.findAll(new Specification<SysLogVO>() {
                //查询条件拼接过程
                @Override
                public Predicate toPredicate(Root root,
                                             CriteriaQuery criteriaQuery,
                                             CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<>();
                    if (vo.getLogModule()!=null) {
                        Predicate codePredicate = cb.equal(root.get("moduleName").as(Integer.class), vo.getLogModule());
                        predicates.add(codePredicate);
                    }
                    if (vo.getHandleType()!=null) {
                        Predicate namePredicate = cb.equal(root.get("handleType").as(Integer.class), vo.getHandleType());
                        predicates.add(namePredicate);
                    }
                    if (!StringUtils.isBlank(vo.getHandleUser())) {
                        Predicate mobilePredicate = cb.like(root.get("handleUsername").as(String.class), "%" + vo.getHandleUser() + "%");
                        predicates.add(mobilePredicate);
                    }
                    if(!StringUtils.isBlank(strbegintime)&&!StringUtils.isBlank(strendtime)){
                        Predicate mobilePredicate = cb.between(root.get("handleTime").as(String.class),strbegintime,strendtime);
                        predicates.add(mobilePredicate);
                    }
                    //判断结合中是否有数据
                    if (predicates.size() == 0) {
                        return null;
                    }
                    //将集合转化为CriteriaBuilder所需要的Predicate[]
                    Predicate[] predicateArr = new Predicate[predicates.size()];
                    predicateArr = predicates.toArray(predicateArr);

                    // 返回所有获取的条件： 条件 and 条件 and 条件
                    return cb.and(predicateArr);

                }
            });
            JpaPageHelper pagehelper = new JpaPageHelper();
            final List<PageInfo> pageInfos = pagehelper.SetStartPage(allList, page, Common_page_size);
            PageInfo pageInfo = pageInfos.get(0);
            retResult.setPage(pageInfo.getPageNow()).setPage_count(pageInfo.getTotlePage())
                    .setPage_size(pageInfo.getPgaeSize()).setTotal_count(allList.size())
                    .setItem_list(pageInfo.getList());
            retData.setCode(StatusCode.OK).setResult(retResult).setMsg("查询成功");
        }catch (Exception e){
            throw new BusinessException(StatusCode.ERROR,e.getMessage());
        }
        return retData;
    }


    public RetData exportExcel(LogQueryVO vo) throws BusinessException {
        XSSFWorkbook xssfWorkbook = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date begindate = new Date(Long.parseLong(vo.getHandleBeginDate()));
        String strbegintime = format.format(begindate).substring(0,10)+" 00:00:00";
        Date enddate = new Date(Long.parseLong(vo.getHandleEndDate()));
        String strendtime = format.format(enddate).substring(0,10)+" 23:59:59";
        Integer page = vo.getPage();
        RetData retData = new RetData();
        RetResult retResult = new RetResult();
        try {
            List<SysLogVO> allList = sysLogDao.findAll(new Specification<SysLogVO>() {
                //查询条件拼接过程
                @Override
                public Predicate toPredicate(Root root,
                                             CriteriaQuery criteriaQuery,
                                             CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<>();
                    if (vo.getLogModule()!=null) {
                        Predicate codePredicate = cb.equal(root.get("moduleName").as(Integer.class), vo.getLogModule());
                        predicates.add(codePredicate);
                    }
                    if (vo.getHandleType()!=null) {
                        Predicate namePredicate = cb.equal(root.get("handleType").as(Integer.class), vo.getHandleType());
                        predicates.add(namePredicate);
                    }
                    if (!StringUtils.isBlank(vo.getHandleUser())) {
                        Predicate mobilePredicate = cb.like(root.get("handleUsername").as(String.class), "%" + vo.getHandleUser() + "%");
                        predicates.add(mobilePredicate);
                    }
                    if(!StringUtils.isBlank(strbegintime)&&!StringUtils.isBlank(strendtime)){
                        Predicate mobilePredicate = cb.between(root.get("handleTime").as(String.class),strbegintime,strendtime);
                        predicates.add(mobilePredicate);
                    }
                    //判断结合中是否有数据
                    if (predicates.size() == 0) {
                        return null;
                    }
                    //将集合转化为CriteriaBuilder所需要的Predicate[]
                    Predicate[] predicateArr = new Predicate[predicates.size()];
                    predicateArr = predicates.toArray(predicateArr);

                    // 返回所有获取的条件： 条件 and 条件 and 条件
                    return cb.and(predicateArr);

                }
            });
            //allList为需要导出数据

            retResult.setItem_list(allList);
            retData.setCode(StatusCode.OK).setResult(retResult).setMsg("获取导出数据成功");
//            List<Map<String,Object>> listMap=ListBeanToListMap(list);
//
//            List<ExcelBean> excel = new ArrayList<>();
//            Map<Integer,List<ExcelBean>> map = new LinkedHashMap<>();
//            //设置标题栏
//            excel.add(new ExcelBean("序号","id",0));
//            excel.add(new ExcelBean("名字","name",0));
//            excel.add(new ExcelBean("年龄","age",0));
//
//            map.put(0,excel);
//            String sheetName = "统计表格";
//            //调用ExcelUtil方法
//            xssfWorkbook = ExportUtil.createExcelFile(DcmDemand.class, listMap, map, sheetName);
        }catch (Exception e){
            throw new BusinessException(StatusCode.ERROR,e.getMessage());
        }
        return retData;
    }


//    public  List<Map<String, Object>> ListBeanToListMap(List<User> list) throws NoSuchMethodException,
//            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
//
//        for (Object ob : list) {
//            listmap.add(BeanMapUtils.beanToMap(ob));
//        }
//        return listmap;
//    }



}
