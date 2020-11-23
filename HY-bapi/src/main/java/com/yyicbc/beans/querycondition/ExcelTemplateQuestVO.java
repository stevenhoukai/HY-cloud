package com.yyicbc.beans.querycondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class ExcelTemplateQuestVO extends BaseQuestVO {
    private String excelTemplateCode;
    private String excelTemplateName;
    private String templateType;
}
