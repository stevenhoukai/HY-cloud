package com.yyicbc.beans.security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor //全参构造
@Accessors(chain = true) //链式编程
public class ChangePwdVO {

    private Long userId;
    private String oldUserPassword;
    private String newUserPassword;
    private String ensureNewUserPassword;

}
