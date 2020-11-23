package com.yyicbc.beans.imports.VO;

import com.yyicbc.beans.imports.PO.ExcelTemplatePO;
import com.yyicbc.beans.imports.PO.PdfTemplatePO;
import com.yyicbc.beans.imports.PO.TxtTemplatePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateTypeVO implements Serializable {
    List<ExcelTemplatePO> excelTypeList = new ArrayList<ExcelTemplatePO>();
    List<TxtTemplatePO> txtTypeList = new ArrayList<TxtTemplatePO>();
    List<PdfTemplatePO> pdfTypeList = new ArrayList<PdfTemplatePO>();
}
