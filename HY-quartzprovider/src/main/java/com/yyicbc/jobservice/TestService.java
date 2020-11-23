package com.yyicbc.jobservice;


import com.netflix.discovery.converters.Auto;
import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;

@Service
public class TestService {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    public void test() throws BusinessException{
        System.out.println("testBusinessException");
        jdbcTemplate.execute("insert into quartz_test(code,name) values(1,'111') ");
        if(true){
            throw new BusinessException(StatusCode.ERROR,"抛出运行时异常");
        }
        jdbcTemplate.execute("insert into quartz_test(code,name) values(2,'222') ");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void test1() throws Exception{
        System.out.println("testException");
        jdbcTemplate.execute("insert into quartz_test(code,name) values(1,'111') ");
        if(true){
            throw new Exception("抛出运行时异常的父类");
        }
        jdbcTemplate.execute("insert into quartz_test(code,name) values(2,'222') ");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void test2() throws FileNotFoundException{
        System.out.println("testFileNotFoundException");
        jdbcTemplate.execute("insert into quartz_test(code,name) values(1,'111') ");
        if(true){
            throw new FileNotFoundException("抛出一个IOException");
        }
        jdbcTemplate.execute("insert into quartz_test(code,name) values(2,'222') ");
    }




    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void test_NEW(){
        System.out.println("test");
    }

}
