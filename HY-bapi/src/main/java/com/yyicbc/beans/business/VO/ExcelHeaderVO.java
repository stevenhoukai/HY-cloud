package com.yyicbc.beans.business.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelHeaderVO implements Serializable {
    private String headerName;//表头名称
    private String headerType;//表头数据类型
}