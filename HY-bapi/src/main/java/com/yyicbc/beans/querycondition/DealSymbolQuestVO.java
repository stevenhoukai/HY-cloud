package com.yyicbc.beans.querycondition;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class DealSymbolQuestVO extends BaseQuestVO
{
    private static final long serialVersionUID = -1420252556112063962L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String symbolCode;

    private String symbolName;
}
