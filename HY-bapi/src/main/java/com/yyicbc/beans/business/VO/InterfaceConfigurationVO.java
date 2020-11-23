package com.yyicbc.beans.business.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceConfigurationVO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String configCode;

    private String configName;

    private String url;

    private String userinfoRequired;

    private String username;

    private String password;

    private String remark;

    private String reserveField1;

    private String reserveField2;

    private String reserveField3;

    private String reserveField4;

    private String reserveField5;

    private String reserveField6;

    private String reserveField7;

    private String reserveField8;

    private String reserveField9;

    private String reserveField10;
}
