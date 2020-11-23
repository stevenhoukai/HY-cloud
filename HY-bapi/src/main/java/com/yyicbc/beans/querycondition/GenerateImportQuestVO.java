package com.yyicbc.beans.querycondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassNameGenerateImportQuestVO
 * @Description
 * @Author vic
 * @Date2020/4/7 16:13
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class GenerateImportQuestVO extends  BaseQuestVO {

    private  String type;
    private  String generdate;
}
