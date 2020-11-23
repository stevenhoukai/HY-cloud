package com.yyicbc.beans.business.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountNameVO {
    private String customerIndex;//客户编码
    private String accountName;//户名
    private String accountNameEng;//英文户名

}
