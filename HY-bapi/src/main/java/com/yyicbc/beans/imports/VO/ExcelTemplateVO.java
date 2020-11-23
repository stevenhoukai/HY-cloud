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
public class ExcelTemplateVO implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String excelTemplateCode;
    private String excelTemplateName;
    private String templateType;
    private List<ExcelFieldVO> excelFieldList;
}
