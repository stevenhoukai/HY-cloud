package com.yyicbc.dao;


import com.yyicbc.beans.business.PO.CusetomerAgreementPO;
import com.yyicbc.beans.business.PO.UserImportPO;
import com.yyicbc.beans.security.SysUserVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 子协议协议dao
 * JpaSpecificationExecutor用于条件拼接
 * author:steven
 */
@Repository
public interface SubProtocalReportDao extends JpaRepository<CusetomerAgreementPO,Long>, JpaSpecificationExecutor<CusetomerAgreementPO> {


    @Query(value = "select a.* from agency_company_customer_info a where a.report_time >= ?1 and a.report_time <=?2 order by a.report_time asc", nativeQuery = true)
    public List<CusetomerAgreementPO> findObjectsByReportDate(String beginDate, String endDate);




}