package com.yyicbc.service.impl;


import com.yyicbc.Exception.BusinessException;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.RetResult;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.business.PO.CompanyBasePO;
import com.yyicbc.beans.querycondition.UserQueryVO;
import com.yyicbc.beans.security.ChangePwdVO;
import com.yyicbc.beans.security.SysRoleMenuVO;
import com.yyicbc.beans.security.SysUserRoleVO;
import com.yyicbc.beans.security.SysUserVO;
import com.yyicbc.component.pagehelper.JpaPageHelper;
import com.yyicbc.component.pagehelper.PageInfo;
import com.yyicbc.dao.*;
import com.yyicbc.service.ExceptionService;
import com.yyicbc.service.UserService;
import com.yyicbc.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/***
 * @author stv
 * 用户管理接口实现类，切面事务管理
 */

@Slf4j
@Service
public class ExceptionServiceImpl implements ExceptionService {

    @Override
    public RetData test() throws BusinessException {
        RetData retData = new RetData();
        try {
            int i = 1/0;
            //或者你认为会发生异常的业务代码
        }catch (Exception e){
            //方式一
            throw new BusinessException(StatusCode.ERROR,e.getMessage());
            //方式二

//            retData.setCode(StatusCode.ERROR);
//            retData.setMsg(e.getMessage());
//            return retData;
//            手动记录日志
        }
        retData.setCode(StatusCode.OK);
        retData.setMsg("ok");
        return retData;
    }
}
