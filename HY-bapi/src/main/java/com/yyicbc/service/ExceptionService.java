package com.yyicbc.service;


import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.querycondition.UserQueryVO;
import com.yyicbc.beans.security.ChangePwdVO;
import com.yyicbc.beans.security.SysUserVO;


/**
 * @author stv
 * 异常处理服务类
 */
public interface ExceptionService {


    /**
     * @return
     */
    RetData test() throws BusinessException;


}
