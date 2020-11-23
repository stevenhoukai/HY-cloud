package com.yyicbc.beans.business.DTO;

import com.yyicbc.beans.imports.PO.FormatFieldPO;
import com.yyicbc.beans.imports.PO.TxtColumnFieldPO;
import com.yyicbc.beans.imports.PO.TxtTitleFieldPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxtHeaderDetailDTO implements Serializable {
    List<TxtTitleFieldPO> headList = new ArrayList<TxtTitleFieldPO>();
    List<TxtColumnFieldPO> columnList = new ArrayList<TxtColumnFieldPO>();
    List<FormatFieldPO> headDetailList = new ArrayList<FormatFieldPO>();
    List<FormatFieldPO> columnDetailList = new ArrayList<FormatFieldPO>();

}