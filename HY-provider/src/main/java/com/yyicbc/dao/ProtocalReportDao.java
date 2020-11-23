package com.yyicbc.dao;


import com.yyicbc.beans.business.PO.UserImportPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 协议dao
 * JpaSpecificationExecutor用于条件拼接
 * author:steven
 */
@Repository
public interface ProtocalReportDao extends JpaRepository<UserImportPO,Long> {

    @Query(value = "select a.company_code,a.company_name,a.company_account,\n" +
            " a.main_agreement_number,a.effect_date,a.product_type,a.product_type_short,\n" +
            "case a.ccy  when  '081' then 'MOP' when '014' then 'USD' else 'HKD' end as ccy,\n" +
            "case a.charge_method when '1' then '1.不收' when '2' then '2.向发起方收' when '3' then '3.向从属方收' when '4' then '4.同时向发起方和从属方收' end,\n" +
            "case a.agreement_status when '0' then 'S.新增服务' when '1' then 'C.取消服务' when '2' then 'M.修改服务' end\n" +
            " from agency_company_protocal_info a \n" +
            "where a.effect_date >= ?1 and a.effect_date <= ?2", nativeQuery = true)
    public List<Object[]> findObjectsByEffectDate(String beginDate, String endDate);


}