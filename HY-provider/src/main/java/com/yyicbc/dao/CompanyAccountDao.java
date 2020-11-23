package com.yyicbc.dao;


import com.yyicbc.beans.business.PO.CompanyAccountPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CompanyAccountDao extends JpaRepository<CompanyAccountPO, Long>, JpaSpecificationExecutor<CompanyAccountPO> {
    List<CompanyAccountPO> findCompanyAccountPOSByCompanyEncode(String companyEncode);

    void deleteCompanyAccountPOSByCompanyEncode(String companyEncode);

    CompanyAccountPO findCompanyAccountPOSByCompanyAccountLike(String companyAccount);

    @Query(value="SELECT DISTINCT company_account FROM company_account WHERE business_type_code = ?1 AND ccy_code = ?2",nativeQuery=true)
    List<String> findCompanyAccountByBusinessTypeCodeAndCcyCode(String businessType, String ccy);

    /**
     * 查找属于代收的公司账号
     * @param ccy
     * @return
     */
    @Query(value="SELECT DISTINCT company_account FROM company_account WHERE business_type_code NOT IN('001,013') AND ccy_code =?1", nativeQuery = true)
    List<String> findCollectionCompanyAccountByCcy(String ccy);
}