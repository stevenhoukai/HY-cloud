package com.yyicbc.dao;

import com.yyicbc.beans.imports.PO.TxtColumnFieldPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TxtColumnFieldDao extends JpaRepository<TxtColumnFieldPO, Long>, JpaSpecificationExecutor<TxtColumnFieldPO> {

    void deleteTxtColumnFieldPOByTxtTemplateCode(String txtTemplateCode);


    List<TxtColumnFieldPO> findTxtColumnFieldPOByTxtTemplateCode(String txtTemplateCode);
}