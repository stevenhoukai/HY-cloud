package com.yyicbc.dao;


import com.yyicbc.beans.security.SysRoleMenuVO;
import com.yyicbc.beans.security.SysRoleVO;
import com.yyicbc.beans.security.SysUserRoleVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * \
 * JpaSpecificationExecutor用于条件拼接
 * author:steven
 */
@Repository
public interface SysRoleUserDao extends JpaRepository<SysUserRoleVO, Long>, JpaSpecificationExecutor<SysUserRoleVO> {

    public List<SysUserRoleVO> findSysUserRoleVOSByRoleId(Long roleId);

    public List<SysUserRoleVO> findSysUserRoleVOSByUserId(Long userId);

    public void deleteSysUserRoleVOSByRoleId(Long roleId);

    public void deleteSysUserRoleVOSByUserId(Long userId);

}