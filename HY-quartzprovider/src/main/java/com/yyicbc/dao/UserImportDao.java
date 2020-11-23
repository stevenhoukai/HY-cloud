package com.yyicbc.dao;


import com.yyicbc.beans.business.PO.UserImportPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserImportDao extends JpaRepository<UserImportPO, Long>, JpaSpecificationExecutor<UserImportPO> {

    void deleteUserImportPOById(Long userImportId);


    /**
     * modify by fmm
     * 按指定条件查询
     * @param updateDate 发送日期
     * @param fileStatus 文件状态
     * @param runNumber 跑批批次
     * @return
     */
    @Query(value="SELECT * FROM agency_customer_import_file importfile WHERE dr = 0 and fova_update_send_date = ?1 " +
            " and file_status =?2 and run_number = ?3 ",nativeQuery=true)
    List<UserImportPO> findUserImportPOList(String updateDate,String fileStatus,String runNumber);

    UserImportPO findUserImportPOById(Long id);
    List<UserImportPO> findUserImportPOSByUploadFileNameEquals(String uploadFileName);

    @Query(value="SELECT * FROM agency_customer_import_file importfile WHERE DATEDIFF(importfile.create_time,NOW())=0 AND company_encode = ?1 ORDER BY importfile.`create_time` DESC LIMIT 1",nativeQuery=true)
    UserImportPO getLatestUserImportPO(String companyEncode);

}