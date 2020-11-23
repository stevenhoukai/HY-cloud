package com.yyicbc.beans.business.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyBaseVO implements Serializable{
    private Long id;
    private String currencyCode;
    private String currencyName;
    private Integer status;
    private String currencyEn;
}
