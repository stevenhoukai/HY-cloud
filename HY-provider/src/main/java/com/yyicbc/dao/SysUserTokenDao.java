package com.yyicbc.dao;


import com.yyicbc.beans.security.SysUserTokenVO;
import com.yyicbc.beans.security.SysUserVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * 用户token管理，判断用户是否在线
 * JpaSpecificationExecutor用于条件拼接
 * author:steven
 */
@Repository
public interface SysUserTokenDao extends JpaRepository<SysUserTokenVO,Long>, JpaSpecificationExecutor<SysUserTokenVO> {


    public SysUserTokenVO findAllByUserId(Long userId);

    public void deleteByUserIdAndToken(Long userId,String token);


}