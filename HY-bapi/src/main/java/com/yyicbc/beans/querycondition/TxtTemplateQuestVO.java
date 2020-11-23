package com.yyicbc.beans.querycondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class TxtTemplateQuestVO extends BaseQuestVO {
    private String txtTemplateCode;
    private String txtTemplateName;
    private String templateType;
}
