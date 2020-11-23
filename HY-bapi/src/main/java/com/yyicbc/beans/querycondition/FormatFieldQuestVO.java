package com.yyicbc.beans.querycondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class FormatFieldQuestVO extends BaseQuestVO {

    private String formatFieldCode;
    private String formatFieldName;
    private String formatFieldType;
    private Integer isEdit;
}
