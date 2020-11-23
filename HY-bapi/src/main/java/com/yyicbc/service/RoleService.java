package com.yyicbc.service;

import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.YyResult;
import com.yyicbc.beans.querycondition.RoleQueryVO;
import com.yyicbc.beans.security.SysRoleVO;

/**
 * @author stv
 * 角色服务类
 */

public interface RoleService {

    /**
     * 查询全部角色
     */
    public RetData all(RoleQueryVO vo) throws BusinessException;


    /**
     * 新增角色
     * @param vo
     * @return
     */
    public RetData saveOrUpdate(SysRoleVO vo) throws BusinessException;


    /**
     *
     * @param roleId
     * @param menuString
     * @return
     * 给角色授权
     */
    RetData execPermission(String roleId, String menuString) throws BusinessException;


    /**
     *
     * @param roleId
     * @return
     * 查询role_user信息
     */
    RetData getAllRoleUserList(long roleId) throws BusinessException;

    /**
     *
     * @param roleId
     * @param userIds
     * @return
     * 给用户授权
     */
    RetData execUserPermission(String roleId, String userIds) throws BusinessException;


    /**
     *
     * @param roleId
     * @return
     * 删除角色
     */
    RetData deleteRoleById(Long roleId) throws BusinessException;
}
