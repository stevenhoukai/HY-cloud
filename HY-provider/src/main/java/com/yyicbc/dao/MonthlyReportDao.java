package com.yyicbc.dao;


import com.yyicbc.beans.business.PO.UserImportPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 月报dao
 * JpaSpecificationExecutor用于条件拼接
 * author:steven
 */
@Repository
public interface MonthlyReportDao extends JpaRepository<UserImportPO,Long> {


    @Query(value = "select a.deduction_date as autopay_date, b.product_type as company_txn_type \n" +
            ",a.pay_account as company_dr_cr_ac ,b.company_name as company_desc,\n" +
            "case b.ccy  when  '081' then 'MOP' when '014' then 'USD' else 'HKD' end as txn_ccy,\n" +
            "sum(a.total_amount) as txn_amt, sum(a.total_count) as rec_cnt \n" +
            " from agency_customer_import_file a left join \n" +
            " agency_company_protocal_info b on a.company_code = b.company_code \n" +
            " where a.file_status = 4 and a.deduction_date like ?1% \n" +
            " group by a.deduction_date,b.product_type,a.pay_account,b.company_name,b.ccy \n" +
            " order by a.deduction_date asc , b.product_type asc,b.ccy asc", nativeQuery = true)
    public List<Object[]> findObjectByDeductionDateFQ(String yearAndMonth);




}