package com.yyicbc.beans.querycondition;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/***
 * @author  stv
 * 用户查询条件封装，page是当前页面参数，为必须值，前台框架已做控制
 */
@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor //全参构造
@Accessors(chain = true) //链式编程
public class UserQueryVO implements Serializable {
    //用户编码
    private String userCode;
    //用户名称
    private String userName;
    //手机号
    private String mobile;
    //页数
    private Integer page;
}
