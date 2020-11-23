package com.yyicbc.service.impl;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.RetResult;
import com.yyicbc.beans.business.PO.*;
import com.yyicbc.beans.business.VO.InterfaceConfigurationVO;
import com.yyicbc.beans.enums.FovaMediuKind;
import com.yyicbc.beans.enums.StatusEnums;
import com.yyicbc.beans.querycondition.CompensationQuestVO;
import com.yyicbc.beans.querycondition.InterfaceConfigurationQuestVO;
import com.yyicbc.beans.utils.distribute.IdWorker;
import com.yyicbc.component.pagehelper.JpaPageHelper;
import com.yyicbc.component.pagehelper.PageInfo;
import com.yyicbc.dao.*;
import com.yyicbc.service.InterfaceConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InterfaceConfigurationServiceImpl implements InterfaceConfigurationService {

    @Resource
    InterfaceConfigurationDao interfaceConfigurationDao;

    @Resource
    IdWorker idWorker;

    @Value("${commons.page.size}")
    private Integer Common_page_size;

    @Override
    public RetData save(InterfaceConfigurationVO interfaceConfigurationVO)
    {
        InterfaceConfigurationPO interfaceConfigurationPO = new InterfaceConfigurationPO();
        BeanUtils.copyProperties(interfaceConfigurationVO, interfaceConfigurationPO);

        if(null == interfaceConfigurationPO.getId()){
            interfaceConfigurationPO.setId(idWorker.nextId());
        }
        interfaceConfigurationDao.save(interfaceConfigurationPO);

        return new RetData(StatusEnums.SUCCESS);
    }


    @Override
    public RetData delete(InterfaceConfigurationQuestVO requestVO)
    {
        interfaceConfigurationDao.deleteInterfaceConfigurationPOById(requestVO.getId());

        return new RetData(StatusEnums.SUCCESS);
    }

    @Override
    public RetData update(InterfaceConfigurationVO interfaceConfigurationVO)
    {
        if(interfaceConfigurationVO.getId() == null){
            return new RetData(StatusEnums.ID_NOT_NULL);
        }
        InterfaceConfigurationPO po = new InterfaceConfigurationPO();
        BeanUtils.copyProperties(interfaceConfigurationVO, po);
        interfaceConfigurationDao.save(po);

        return new RetData(StatusEnums.SUCCESS);
    }

    @Override
    public RetData getData(InterfaceConfigurationQuestVO questVO)
    {
        List<InterfaceConfigurationPO> allList = interfaceConfigurationDao.findAll(new Specification<InterfaceConfigurationPO>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isBlank(questVO.getConfigCode())) {
                    Predicate codePredicate = cb.like(root.get("configCode").as(String.class), "%" + questVO.getConfigCode() + "%");
                    predicates.add(codePredicate);
                }
                if (!StringUtils.isBlank(questVO.getConfigName())) {
                    Predicate codePredicate = cb.like(root.get("configName").as(String.class), "%" + questVO.getConfigName() + "%");
                    predicates.add(codePredicate);
                }
                if (predicates.size() == 0) {
                    return null;
                }
                Predicate[] predicateArr = new Predicate[predicates.size()];
                predicateArr = predicates.toArray(predicateArr);

                return cb.and(predicateArr);
            }
        });
        JpaPageHelper page = new JpaPageHelper();
        final List<PageInfo> pageInfos = page.SetStartPage(allList, questVO.getPage(), Common_page_size);
        PageInfo pageInfo = pageInfos.get(0);
        RetResult retResult = new RetResult();
        retResult.setPage(pageInfo.getPageNow())
                .setPage_count(pageInfo.getTotlePage())
                .setPage_size(pageInfo.getPgaeSize())
                .setTotal_count(allList.size())
                .setItem_list(pageInfo.getList());

        return new RetData(StatusEnums.SUCCESS, retResult);
    }

}
