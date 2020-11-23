package com.yyicbc.dao;


import com.yyicbc.beans.business.PO.UserImportPO;
import com.yyicbc.beans.security.SysUserVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 日报dao
 * JpaSpecificationExecutor用于条件拼接
 * author:steven
 */
@Repository
public interface DailyReportDao extends JpaRepository<UserImportPO,Long> {


    @Query(value = "select a.deduction_date as autopay_date,a.flow_number as batch_no,a.company_code as com_code\n" +
            ",a.pay_account as icbc_ac_no  ,a.total_amount as txn_amt, a.total_count as rec_cnt,\n" +
            "case b.ccy  when  '081' then 'MOP' when '014' then 'USD' else 'HKD' end as ccy,'發起方' as direction, \n" +
            "case when b.borrow_sign=1 then 'DR' else 'CR' end as drcr_flag ,c.user_code as maker,d.user_code as checker,a.id as id\n" +
            " from agency_customer_import_file a left join \n" +
            " agency_company_protocal_info b on a.company_code = b.company_code \n" +
            " left join sys_user c on a.maker_id = c.id " +
            " left join sys_user d on a.checker_id = d.id where a.file_status = 4 and a.company_code = ?3 and a.deduction_date >= ?1 and a.deduction_date <= ?2 \n" +
            " order by a.deduction_date,a.flow_number,a.company_code,a.pay_account", nativeQuery = true)
    public List<Object[]> findObjectByDeductionDateByCorpCodeFQ(String deductionBeginDate,String deductionEndDate,String corpCode);


    @Query(value = "select a.deduction_date as autopay_date,a.flow_number as batch_no,a.company_code as com_code\n" +
            ",a.pay_account as icbc_ac_no  ,a.total_amount as txn_amt, a.total_count as rec_cnt,\n" +
            "case b.ccy  when  '081' then 'MOP' when '014' then 'USD' else 'HKD' end as ccy,'發起方' as direction, \n" +
            "case when b.borrow_sign=1 then 'DR' else 'CR' end as drcr_flag ,c.user_code as maker,d.user_code as checker,a.id as id\n" +
            " from agency_customer_import_file a left join \n" +
            " agency_company_protocal_info b on a.company_code = b.company_code \n" +
            " left join sys_user c on a.maker_id = c.id " +
            " left join sys_user d on a.checker_id = d.id where a.file_status = 4 and a.deduction_date >= ?1 and a.deduction_date <= ?2 \n" +
            " order by a.deduction_date,a.flow_number,a.company_code,a.pay_account", nativeQuery = true)
    public List<Object[]> findObjectByDeductionDateFQ(String deductionBeginDate,String deductionEndDate);

    @Query(value = "select a.deduction_date as autopay_date,a.flow_number as batch_no,a.company_code as com_code,\n" +
            "b.fova_return_customeraccount as icbc_ac_no,sum(b.fova_update_amount2) as txn_amt,sum(b.fova_update_counter) as rec_cnt,\n" +
            "case b.fova_update_currtype  when  '081' then 'MOP' when '014' then 'USD' else 'HKD' end as ccy ,'從屬方' as derection,case when b.fova_update_drcrf=1 then 'DR' else 'CR' end as drcr_flag,\n" +
            "c.user_code as maker,d.user_code as checker, '' as id\n" +
            " from agency_customer_import_file a left join fova_update_data b on \n" +
            "a.id = b.user_import_id left join sys_user c on a.maker_id = c.id left join sys_user d on a.checker_id = d.id \n" +
            "where a.file_status = 4 and a.company_code = ?3 and a.deduction_date >= ?1 and a.deduction_date <= ?2 \n" +
            " group by a.deduction_date,a.flow_number,a.company_code,b.fova_return_customeraccount,b.fova_update_currtype,b.fova_update_drcrf,c.user_code,d.user_code \n" +
            " order by a.deduction_date,a.flow_number,a.company_code,b.fova_return_customeraccount", nativeQuery = true)
    public List<Object[]> findObjectByDeductionDateByCorpCodeCS(String deductionDate,String deductionEndDate,String corpCode);


    @Query(value = "select a.deduction_date as autopay_date,a.flow_number as batch_no,a.company_code as com_code,\n" +
            "b.fova_return_customeraccount as icbc_ac_no,sum(b.fova_update_amount2) as txn_amt,sum(b.fova_update_counter) as rec_cnt,\n" +
            "case b.fova_update_currtype  when  '081' then 'MOP' when '014' then 'USD' else 'HKD' end as ccy ,'從屬方' as derection,case when b.fova_update_drcrf=1 then 'DR' else 'CR' end as drcr_flag,\n" +
            "c.user_code as maker,d.user_code as checker, '' as id\n" +
            " from agency_customer_import_file a left join fova_update_data b on \n" +
            " a.id = b.user_import_id " +
            " left join sys_user c on a.maker_id = c.id " +
            " left join sys_user d on a.checker_id = d.id \n" +
            " where a.file_status = 4 and a.deduction_date >= ?1 and a.deduction_date <= ?2 \n" +
            " group by a.deduction_date,a.flow_number,a.company_code,b.fova_return_customeraccount,b.fova_update_currtype,b.fova_update_drcrf,c.user_code,d.user_code \n" +
            " order by a.deduction_date,a.flow_number,a.company_code,b.fova_return_customeraccount", nativeQuery = true)
    public List<Object[]> findObjectByDeductionDateCS(String deductionDate,String deductionEndDate);




    @Query(value = "select a.deduction_date as autopay_date,a.flow_number as batch_no,a.company_code as com_code\n" +
            ",a.pay_account as icbc_ac_no  ,a.total_amount as txn_amt, a.total_count as rec_cnt,\n" +
            "case b.ccy  when  '081' then 'MOP' when '014' then 'USD' else 'HKD' end as ccy,'發起方' as direction, \n" +
            "case when b.borrow_sign=1 then 'DR' else 'CR' end as drcr_flag ,c.user_code as maker,d.user_code as checker,a.id as id, '' as updatestatus\n" +
            " from agency_customer_import_file a left join \n" +
            " agency_company_protocal_info b on a.company_code = b.company_code \n" +
            " left join sys_user c on a.maker_id = c.id " +
            " left join sys_user d on a.checker_id = d.id where a.file_status = 4 and a.id in ?1 \n" +
            " order by a.deduction_date,a.flow_number,a.company_code,a.pay_account", nativeQuery = true)
    public List<Object[]> findObjectByIdsFQ(List list);



    @Query(value = "select a.deduction_date as autopay_date,a.flow_number as batch_no,a.company_code as com_code,\n" +
            "b.fova_return_customeraccount as icbc_ac_no,sum(b.fova_update_amount) as txn_amt,sum(b.fova_update_counter) as rec_cnt,\n" +
            "case b.fova_update_currtype  when  '081' then 'MOP' when '014' then 'USD' else 'HKD' end as ccy ,'從屬方' as derection,case when b.fova_update_drcrf=1 then 'DR' else 'CR' end as drcr_flag,\n" +
            "c.user_code as maker,d.user_code as checker, '' as id, b.fova_update_status as updatestatus \n" +
            " from agency_customer_import_file a left join fova_update_data b on \n" +
            " a.id = b.user_import_id " +
            " left join sys_user c on a.maker_id = c.id " +
            " left join sys_user d on a.checker_id = d.id \n" +
            " where a.file_status = 4 and a.id in ?1 \n" +
            " group by a.deduction_date,a.flow_number,a.company_code,b.fova_return_customeraccount,b.fova_update_currtype,b.fova_update_drcrf,c.user_code,d.user_code,b.fova_update_status \n" +
            " order by a.deduction_date,a.flow_number,a.company_code,b.fova_return_customeraccount", nativeQuery = true)
    public List<Object[]> findObjectByIdsCS(List list);

}