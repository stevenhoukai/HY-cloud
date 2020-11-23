package com.yyicbc.beans.querycondition;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/***
 * @author  stv
 * 日志查询条件封装，page是当前页面参数，为必须值，前台框架已做控制
 */
@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor //全参构造
@Accessors(chain = true) //链式编程
public class LogQueryVO implements Serializable {
    //模块
    private Integer logModule;
    //操作类型
    private Integer handleType;
    //操作用户
    private String handleUser;
    //操作开始时间
    private String handleBeginDate;
    //操作结束时间
    private String handleEndDate;

//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date handleBeginDate;
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date handleEndDate;

    //页数
    private Integer page;
}
