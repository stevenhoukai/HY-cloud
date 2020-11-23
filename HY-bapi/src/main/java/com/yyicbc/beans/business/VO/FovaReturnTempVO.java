package com.yyicbc.beans.business.VO;

import com.yyicbc.beans.business.PO.FovaReturnTempPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 *@author vic fu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FovaReturnTempVO implements Serializable {

    private static final long serialVersionUID = 5420197415637503877L;

    private  Integer status;

    private String msg;

    private String errCode;

    private String errorState;

    private String stack;

    private List<FovaReturnTempPO> data;

}
