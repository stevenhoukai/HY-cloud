package com.yyicbc.dao;

import com.yyicbc.beans.business.PO.CompensationBPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface CompensationBDao extends JpaRepository<CompensationBPO,Long>, JpaSpecificationExecutor<CompensationBPO> {


    void deleteCompensationBPOSByCompensationId(Long compensationId);
}
