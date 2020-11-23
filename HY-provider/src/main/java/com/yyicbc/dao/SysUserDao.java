package com.yyicbc.dao;


import com.yyicbc.beans.querycondition.UserQueryVO;
import com.yyicbc.beans.security.SysUserVO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 用户管理dao
 * JpaSpecificationExecutor用于条件拼接
 * author:steven
 */
@Repository
public interface SysUserDao extends JpaRepository<SysUserVO,Long>, JpaSpecificationExecutor<SysUserVO> {


    public SysUserVO findSysUserVOByUserCode(String userCode) ;

    public SysUserVO findSysUserVOByUserId(Long userId);

    public List<SysUserVO> findSysUserVOSByUsertype(Integer userType);

    public List<SysUserVO> findSysUserVOSByUsertypeAndStatus(Integer userType, Integer status);



    @Modifying
    @Query(value = "SELECT * FROM sys_user WHERE id IN(?1)", nativeQuery = true)
    public List<SysUserVO> findSysUserVOSByUserIdIn(List<Long> ids);


}