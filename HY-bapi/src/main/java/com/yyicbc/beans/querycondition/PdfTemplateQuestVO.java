package com.yyicbc.beans.querycondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class PdfTemplateQuestVO extends BaseQuestVO {
    private String pdfTemplateCode;
    private String pdfTemplateName;
    private String templateType;
}
