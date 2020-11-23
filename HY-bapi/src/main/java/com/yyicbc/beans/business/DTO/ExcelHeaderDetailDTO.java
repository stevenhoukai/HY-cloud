package com.yyicbc.beans.business.DTO;

import com.yyicbc.beans.imports.PO.ExcelFieldPO;
import com.yyicbc.beans.imports.PO.FormatFieldPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelHeaderDetailDTO implements Serializable {
    List<ExcelFieldPO> headList = new ArrayList<ExcelFieldPO>();
    List<FormatFieldPO> headDetailList = new ArrayList<FormatFieldPO>();

}