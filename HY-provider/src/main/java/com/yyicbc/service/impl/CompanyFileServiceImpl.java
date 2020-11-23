package com.yyicbc.service.impl;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.RetResult;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.business.PO.CompanyAccountPO;
import com.yyicbc.beans.business.PO.CompanyBasePO;
import com.yyicbc.beans.business.VO.CompanyAccountVO;
import com.yyicbc.beans.business.VO.CompanyBaseVO;
import com.yyicbc.beans.enums.StatusEnums;
import com.yyicbc.beans.imports.PO.ExcelTemplatePO;
import com.yyicbc.beans.imports.PO.PdfTemplatePO;
import com.yyicbc.beans.imports.PO.TxtTemplatePO;
import com.yyicbc.beans.imports.VO.TemplateTypeVO;
import com.yyicbc.beans.querycondition.CompanyQuestVO;
import com.yyicbc.beans.utils.distribute.IdWorker;
import com.yyicbc.component.pagehelper.JpaPageHelper;
import com.yyicbc.component.pagehelper.PageInfo;
import com.yyicbc.dao.*;
import com.yyicbc.service.CompanyFileService;
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
import java.util.Date;
import java.util.List;

@Service
public class CompanyFileServiceImpl implements CompanyFileService {
    @Value("${commons.page.size}")
    private Integer Common_page_size;

    @Resource
    CompanyFileDao companyFileDao;
    @Resource
    CompanyAccountDao companyAccountDao;
    @Resource
    IdWorker idWorker;

    @Override
    public RetData getData(CompanyQuestVO vo) {
        List<CompanyBasePO> allList = companyFileDao.findAll(new Specification<CompanyBasePO>() {
            @Override
            public Predicate toPredicate(Root root,
                                         CriteriaQuery criteriaQuery,
                                         CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isBlank(vo.getCompanyEncode())) {
                    Predicate codePredicate = cb.like(root.get("companyEncode").as(String.class), "%" + vo.getCompanyEncode() + "%");
                    predicates.add(codePredicate);
                }
                if (!StringUtils.isBlank(vo.getCompanyName())) {
                    Predicate namePredicate = cb.like(root.get("companyName").as(String.class), "%" + vo.getCompanyName() + "%");
                    predicates.add(namePredicate);
                }
                Predicate[] predicateArr = new Predicate[predicates.size()];
                predicateArr = predicates.toArray(predicateArr);
                criteriaQuery.where(cb.and(predicateArr));
                criteriaQuery.orderBy(cb.desc(root.get("createTime").as(Date.class)));
                return criteriaQuery.getRestriction();
            }
        });
        List<CompanyBaseVO> returnList = new ArrayList<>();
        for (CompanyBasePO item : allList) {
            CompanyBaseVO returnItem = new CompanyBaseVO();
            BeanUtils.copyProperties(item, returnItem);
            List<CompanyAccountVO> subList = new ArrayList<CompanyAccountVO>();
            List<CompanyAccountPO> poList = companyAccountDao.findCompanyAccountPOSByCompanyEncode(item.getCompanyEncode());
            if (!poList.isEmpty()) {
                for (CompanyAccountPO accountPO : poList) {
                    CompanyAccountVO accountVO = new CompanyAccountVO();
                    BeanUtils.copyProperties(accountPO, accountVO);
                    subList.add(accountVO);
                }
            }
            returnItem.setAccountList(subList);
            returnList.add(returnItem);
        }
        JpaPageHelper page = new JpaPageHelper();
        final List<PageInfo> pageInfos = page.SetStartPage(returnList, vo.getPage(), Common_page_size);
        PageInfo pageInfo = pageInfos.get(0);
        RetResult retResult = new RetResult();
        retResult.setPage(pageInfo.getPageNow())
                .setPage_count(pageInfo.getTotlePage())
                .setPage_size(pageInfo.getPgaeSize())
                .setTotal_count(returnList.size())
                .setItem_list(pageInfo.getList());
        return new RetData(StatusEnums.SUCCESS, retResult);
    }

    public RetData addOrUpdateData(CompanyBaseVO vo) {
        CompanyBasePO po = companyFileDao.findCompanyBasePOByCompanyEncodeEquals(vo.getCompanyEncode());
        if (po != null&&!po.getId().equals(vo.getId())) {//新增时代码已存在
            return new RetData(StatusEnums.VIOLATE_UNIQUE);
        }
        CompanyBasePO companyPO = new CompanyBasePO();
        BeanUtils.copyProperties(vo, companyPO);
        if (companyPO.getId() == null) {//新增时需要随机ID
            companyPO.setId(idWorker.nextId());
        } else {//非新增需要把未被选模板code去掉
            if ("0".equals(companyPO.getFileType())) {
                companyPO.setExcelTemplateType("");
            }
            if ("1".equals(companyPO.getFileType())) {
                companyPO.setTxtTemplateType("");
            }
            if ("0".equals(companyPO.getExportFileType())) {
                companyPO.setExportExcelTemplateType("");
                companyPO.setExportPdfTemplateType("");
            }
            if ("1".equals(companyPO.getExportFileType())) {
                companyPO.setExportTxtTemplateType("");
                companyPO.setExportPdfTemplateType("");
            }
            if ("2".equals(companyPO.getExportFileType())) {
                companyPO.setExportTxtTemplateType("");
                companyPO.setExportExcelTemplateType("");
            }
        }
        companyFileDao.save(companyPO);
        //保存公司账号信息
        if (vo.getAccountList() != null) {
            List<CompanyAccountVO> accountList = vo.getAccountList();
            List<CompanyAccountPO> saveList = new ArrayList<CompanyAccountPO>();
            for (CompanyAccountVO item : accountList) {
                CompanyAccountPO saveItem = new CompanyAccountPO();
                BeanUtils.copyProperties(item, saveItem);
                saveItem.setCompanyEncode(vo.getCompanyEncode());
                saveItem.setId(idWorker.nextId());
                saveList.add(saveItem);
            }
            //保存前需要将之前的数据删除掉
            //更新时可能会有条目变化，会导致ID不正确，只能先将原有的全删除
            companyAccountDao.deleteCompanyAccountPOSByCompanyEncode(vo.getCompanyEncode());
            companyAccountDao.saveAll(saveList);
        }
        return new RetData(StatusEnums.SUCCESS);
    }

    public RetData deleteData(CompanyQuestVO vo) {
        //删公司信息
        companyFileDao.deleteCompanyBasePOById(vo.getId());
        //删账号信息
        companyAccountDao.deleteCompanyAccountPOSByCompanyEncode(vo.getCompanyEncode());
        return new RetData(StatusEnums.SUCCESS);
    }

    @Override
    public RetData getTemplate() {
        return null;
    }


    @Override
    public RetData findAll() {
        List<CompanyBasePO> allList = companyFileDao.findAll();
        RetResult retResult = new RetResult();
        retResult.setItem_list(allList);
        return new RetData(StatusEnums.SUCCESS, retResult);
    }
}