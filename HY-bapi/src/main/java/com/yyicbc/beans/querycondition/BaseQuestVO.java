package com.yyicbc.beans.querycondition;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class BaseQuestVO implements Serializable {
    private Long id;//主键ID
    private Integer page;
}
