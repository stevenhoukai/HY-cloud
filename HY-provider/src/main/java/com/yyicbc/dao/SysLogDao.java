package com.yyicbc.dao;


import com.yyicbc.beans.logmanager.SysLogVO;
import com.yyicbc.beans.security.SysUserVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 日志管理dao
 * JpaSpecificationExecutor用于条件拼接
 * author:steven
 */
@Repository
public interface SysLogDao extends JpaRepository<SysLogVO,Long>, JpaSpecificationExecutor<SysLogVO> {


//    public SysUserVO findSysUserVOByUserCode(String userCode) ;
//
//    public SysUserVO findSysUserVOByUserId(Long userId);
//
//    public List<SysUserVO> findSysUserVOSByUsertype(Integer userType);
//
//    public List<SysUserVO> findSysUserVOSByUsertypeAndStatus(Integer userType, Integer status);
//
//    @Modifying
//    @Query(value = "SELECT * FROM sys_user WHERE id IN(?1)", nativeQuery = true)
//    public List<SysUserVO> findSysUserVOSByUserIdIn(List<Long> ids);


}