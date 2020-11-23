package com.yyicbc.beans.imports.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdfTemplateVO implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String pdfTemplateCode;
    private String pdfTemplateName;
    private String templateType;
    private List<PdfTitleFieldVO> pdfTitleFieldList;
    private List<PdfColumnFieldVO> pdfColumnFieldList;
    private List<PdfFooterFieldVO> pdfFooterFieldList;
}
