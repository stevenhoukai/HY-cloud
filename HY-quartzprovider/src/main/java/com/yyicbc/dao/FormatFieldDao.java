package com.yyicbc.dao;

import com.yyicbc.beans.imports.PO.FormatFieldPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface FormatFieldDao extends JpaRepository<FormatFieldPO, Long>, JpaSpecificationExecutor<FormatFieldPO> {

    void deleteFormatFieldPOByFormatFieldCode(String formatFieldcode);

    FormatFieldPO getFormatFieldPOByFormatFieldCode(String formatFieldCode);
}