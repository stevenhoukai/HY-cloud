package com.yyicbc.beans.business.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealSymbolVO implements Serializable
{
    private static final long serialVersionUID = -5142851197764299502L;

    private Long id;

    private String symbolCode;

    private String symbolName;

    private String fovaSymbolName;

}
