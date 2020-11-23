package com.yyicbc.service;


import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.querycondition.UserQueryVO;
import com.yyicbc.beans.security.ChangePwdVO;
import com.yyicbc.beans.security.SysUserVO;

import java.awt.image.BufferStrategy;


/**
 * @author stv
 * 用户服务类
 */
public interface UserService {

    /**
     * 获取验证码
     * @return
     */
//    public String getCode();



    /**
     * 查询所有用户
     * @return
     */
    public RetData all(UserQueryVO vo) throws BusinessException;

    /**
     * 保存用户
     * @param user
     * @return
     */
    public RetData saveOrUpdate(SysUserVO user) throws BusinessException;

    /**
     * 更新用户
     * @param user
     * @return
     */
//    public RetData update(SysUserVO user);

    /**
     * 根据ID删除用户
     * @param id
     * @return
     */
//    public Object delete(Long id);

    /**
     * 检查校验码是否正确
     * @param type
     * @param value
     * @return
     */
//    public YyResult checkCode(String type, String value, HttpServletRequest request);


    /**
     * 用户登陆
     * @param username
     * @param password
     * @param type
     */
//    public YyResult queryUser(String username,String password, String type);


    /**
     * 用户
     * @param userId
     */
    RetData deleteUserById(Long userId) throws BusinessException;

    /**
     * 用户
     * @param userId
     */
    RetData resetPwdById(Long userId) throws BusinessException;

    /**
     *
     * @param usercode
     * @param password
     * @return
     */
    RetData execUserLogin(String usercode, String password) throws BusinessException;

    
    /**
     *
     * @param usercode
     * @param password
     * @return
     */
    RetData execUserLoginsso(String usercode, String password) throws BusinessException;


    /**
     *
     * @param usercode
     * @return
     */
    RetData execUserLogout(String usercode) throws BusinessException;

    /**
     * @return
     */
    RetData findAllCorps() throws BusinessException;


    /**
     * @return
     */
    RetData saveOrUpdate(ChangePwdVO vo) throws BusinessException;


}
