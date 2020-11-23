package com.yyicbc.dao;


import com.yyicbc.beans.security.SysRoleVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 角色相关dao
 * author:steven
 */
@Repository
public interface SysRoleDao extends JpaRepository<SysRoleVO,Long>, JpaSpecificationExecutor<SysRoleVO> {

    @Modifying
    @Query(value = "SELECT role_code FROM sys_role WHERE id IN(?1)", nativeQuery = true)
    List<String> findRoleCodesByUserIdIn(List<Long> ids);
}