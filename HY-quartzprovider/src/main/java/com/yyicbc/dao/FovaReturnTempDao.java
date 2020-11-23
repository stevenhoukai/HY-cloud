package com.yyicbc.dao;

import com.yyicbc.beans.business.PO.CompensationBPO;
import com.yyicbc.beans.business.PO.FovaReturnTempPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 *@author vic fu
 * 回传中间表操作
 */
@Repository
public interface FovaReturnTempDao extends JpaRepository<FovaReturnTempPO,Long>, JpaSpecificationExecutor<FovaReturnTempPO> {

    /**
     *
     * @param senddate
     */
    void deleteTempBySendate(String  senddate);




}
