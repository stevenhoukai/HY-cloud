package com.yyicbc.dao;

import com.yyicbc.beans.business.PO.CompensationPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CompensationDao extends JpaRepository<CompensationPO,Long>, JpaSpecificationExecutor<CompensationPO> {

    @Modifying
    @Query(value = "UPDATE compensation SET send_status= ?1 WHERE id IN(?2)", nativeQuery = true)
    void updateSendStatusByIds(int sendStatus, List<Long> ids);

    /**
     *  //modify by fmm
     * @param updateDate 上传日期
     * @param batchStatus 状态
     * @return
     */
//    List<CompensationPO> findCompensationPOByBatchStatus(int batchStatus);
    @Query(value = "select * from compensation where dr = 0 and left(upload_date,10) = ?1 and batch_status = ?2 and replace(compensation.distribute_times,':','') = ?3 ", nativeQuery = true)
    List<CompensationPO> findCompensationPOList(String updateDate,int batchStatus,String distribute_times);

    @Query(value = "select * from compensation where batch_status = ?1 and IFNULL(send_status, '') != 1", nativeQuery = true)
    List<CompensationPO> findNotSendCompensationPOSByBatchStatus(Integer batchStatus);

    List<CompensationPO> findCompensationPOSByUploadFileNameEquals(String name);
}
