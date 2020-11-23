package com.yyicbc.jobservice.Impl;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.business.PO.CompensationBPO;
import com.yyicbc.beans.business.PO.CompensationPO;
import com.yyicbc.beans.business.VO.CompensationVO;
import com.yyicbc.beans.business.VO.SendHCMCompensationBVO;
import com.yyicbc.beans.business.VO.SendHCMUpdateVO;
import com.yyicbc.beans.enums.StatusEnums;
import com.yyicbc.beans.querycondition.CompensationQuestVO;
import com.yyicbc.dao.CompensationBDao;
import com.yyicbc.dao.CompensationDao;
import com.yyicbc.jobservice.CompensationService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompensationServiceImpl implements CompensationService {

    @Resource
    CompensationDao compensationDao;

    @Resource
    CompensationBDao compensationBDao;

    @Override
    public RetData update(List<CompensationVO> compensationVOs) {
        List<CompensationPO> compensationPOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(compensationVOs)) {
            RetData retData = new RetData(StatusEnums.ERROR);
            return retData;
        }
        for (CompensationVO vo : compensationVOs) {
            CompensationPO compensationPO = new CompensationPO();
            BeanUtils.copyProperties(vo, compensationPO);
            compensationPOList.add(compensationPO);
        }
        //保存主表数据
        compensationDao.saveAll(compensationPOList);
        //compensationDao.sa
        RetData retData = new RetData(StatusEnums.SUCCESS);
        return retData;
    }

    @Override
    public RetData updateSendStatus(List<Long> compensationIds) {
        compensationDao.updateSendStatusByIds(1, compensationIds);

        RetData retData = new RetData(StatusEnums.SUCCESS);
        return retData;
    }

    @Override
    public List<SendHCMUpdateVO> getAll(CompensationQuestVO questVO) {

        List<CompensationPO> allList = compensationDao.findNotSendCompensationPOSByBatchStatus(questVO.getBatchStatus());
        if(CollectionUtils.isEmpty(allList)){
            return null;
        }
        //id list集合
        List<Long> ids = new ArrayList<>();
        for (CompensationPO po:allList) {
            ids.add(po.getId());
        }

        List<CompensationBPO> lists = compensationBDao.findAll(new Specification<CompensationBPO>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(root.get("compensationId").in(ids));

                Predicate[] predicateArr = new Predicate[predicates.size()];

                return cb.and(predicates.toArray(predicateArr));
            }
        });

        if(CollectionUtils.isEmpty(lists)){
            return null;
        }

        List<SendHCMUpdateVO> sendHCMUpdateVOs = new ArrayList<>();
        for (CompensationPO compensationPO : allList) {
            SendHCMUpdateVO sendHCMUpdateVO = new SendHCMUpdateVO();
            sendHCMUpdateVO.setBatchStatus(questVO.getBatchStatus());
            sendHCMUpdateVO.setCompensationId(String.valueOf(compensationPO.getId()));
            sendHCMUpdateVO.setPk_payroll(compensationPO.getPk_payroll());
            List<String> pk_wa_datas = new ArrayList<>();
            List<SendHCMCompensationBVO> compensationBs = new ArrayList<>();
            for(CompensationBPO bpo : lists){
                if(compensationPO.getId().equals(bpo.getCompensationId())){
                    pk_wa_datas.add(bpo.getPk_wa_data());
                    SendHCMCompensationBVO sendbvo = new SendHCMCompensationBVO();
                    sendbvo.setId(String.valueOf(bpo.getId()));
                    sendbvo.setCompensationId(String.valueOf(bpo.getCompensationId()));
                    sendbvo.setPersonnelCode(bpo.getPersonnelCode());
                    sendbvo.setAccountName(bpo.getAccountName());
                    sendbvo.setAccountNo(bpo.getAccountNo());
                    sendbvo.setCurrencyCode(bpo.getCurrencyCode());
                    sendbvo.setAmountResult(bpo.getAmountResult());
                    sendbvo.setSucceedStatus(bpo.getSucceedStatus());
                    sendbvo.setNote(bpo.getNote());
                    sendbvo.setErrorMessage(bpo.getErrorMessage());
                    sendbvo.setPk_wa_data(bpo.getPk_wa_data());
                    sendbvo.setReserveField1(bpo.getReserveField1());
                    sendbvo.setReserveField2(bpo.getReserveField2());
                    sendbvo.setReserveField3(bpo.getReserveField3());
                    sendbvo.setReserveField4(bpo.getReserveField4());
                    sendbvo.setReserveField5(bpo.getReserveField5());
                    compensationBs.add(sendbvo);
                }
            }
            sendHCMUpdateVO.setPk_wa_datas(pk_wa_datas);
            sendHCMUpdateVO.setCompensationBs(compensationBs);
            sendHCMUpdateVOs.add(sendHCMUpdateVO);
        }

        return sendHCMUpdateVOs;
    }


}
