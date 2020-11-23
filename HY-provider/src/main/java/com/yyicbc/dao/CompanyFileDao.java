package com.yyicbc.dao;


import com.yyicbc.beans.business.PO.CompanyBasePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CompanyFileDao extends JpaRepository<CompanyBasePO, Long>, JpaSpecificationExecutor<CompanyBasePO> {

    void deleteCompanyBasePOByCompanyEncode(String companyEncode);


    void deleteCompanyBasePOById(Long Id);

    CompanyBasePO findCompanyBasePOByCompanyAccountLike(String companyAccount);

    CompanyBasePO findCompanyBasePOByCompanyEncodeEquals(String companyEncode);

    CompanyBasePO findCompanyBasePOById(Long id);

    List<CompanyBasePO> findCompanyBasePOSByTxtTemplateType(String txtTemplateType);

    List<CompanyBasePO> findCompanyBasePOSByExportTxtTemplateType(String txtTemplateType);

    List<CompanyBasePO> findCompanyBasePOSByExcelTemplateType(String excelTemplateType);

    List<CompanyBasePO> findCompanyBasePOSByExportExcelTemplateType(String exportExcelType);



}