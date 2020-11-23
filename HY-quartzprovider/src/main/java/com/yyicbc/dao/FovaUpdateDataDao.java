package com.yyicbc.dao;


import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Repository
public interface FovaUpdateDataDao  extends JpaRepository<FovaUpdateDataPO, Long>, JpaSpecificationExecutor<FovaUpdateDataPO> {

    void deleteFovaUpdateDataPOSByUserImportId(String userImportId);


    List<FovaUpdateDataPO> findFovaUpdateDataPOSByUserImportId(String userImportId);


    List<FovaUpdateDataPO> findFovaUpdateDataPOSByUploadFileName(String uploadFileName);

    @Query(value = "UPDATE fova_update_data SET fova_update_trandate= ?1 , fova_update_valuedat=?2 WHERE user_import_id= ?3", nativeQuery = true)
    @Modifying
    void updateDateInfoByflowID(String tranDate, String valueDated, String flowID);

    /**
     * add by fmm 根据日期获取最大的no
     * fova_update_seqno 是字符串类型，必须先转成整形在获取最大值
     * @param updateDate
     * @return
     */
    @Query(value = "SELECT ifnull(Max(CAST(IFNULL(fova_update_seqno,0) AS SIGNED)),0) SeqNo FROM  fova_update_data WHERE fova_update_senddate = ?1 ", nativeQuery = true)
    List<BigInteger> findMaxSeqnoByUpdatetime(String updateDate);


    /**
     * add by fmm
     * 来源发放薪资：
     * 查询汇总数据，只允许查出一条，如果有多条说明查询条件不够准确
     * *查询条件：先关联发放薪资表，根据薪资表转换之前的金额查询
     * 汇总标识：fova_update_recordf =1
     * 来源发放薪资：src_type = COM
     * @param agenpsn
     * @param amount
     * @param sendate
     * @return
     */
    @Query(value = "select tab_fova.* from fova_update_data tab_fova inner join compensation tab_com " +
            " on tab_fova.user_import_id = tab_com.id " +
            " where tab_fova.dr = 0 and tab_fova.fova_update_recordf = '1' and tab_fova.src_type='COM' " +
            " and fova_update_agenpsn= ?1 and  tab_com.total_amount = ?2 and tab_fova.fova_update_senddate = ?3 ", nativeQuery = true)
    List<FovaUpdateDataPO> findTotalDataByCom(String agenpsn,Double amount,String sendate);

    /**
     * add by fmm
     * 来源 客户导入
     * @param agenpsn
     * @param amount
     * @param sendate
     * @return
     */
    @Query(value = "select tab_fova.* from fova_update_data tab_fova  " +
            " where tab_fova.dr = 0 and tab_fova.fova_update_recordf = '1' and ifnull(tab_fova.src_type,'USER')='USER' " +
            " and fova_update_agenpsn= ?1 and  tab_fova.fova_update_amount = ?2 and tab_fova.fova_update_senddate = ?3 ", nativeQuery = true)
    List<FovaUpdateDataPO> findTotalDataByUser(String agenpsn,String amount,String sendate);


    /**
     * 查询明细，不包含汇总记录
     * @param userImportId
     * @return
     */
    @Query(value = "select * from fova_update_data  where dr = 0 and fova_update_recordf = '2'  " +
            " and fova_update_data.user_import_id = ?1 ", nativeQuery = true)
    List<FovaUpdateDataPO> findDetailDataByUserImportId(String userImportId);
}
