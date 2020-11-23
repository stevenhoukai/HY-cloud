package com.yyicbc.beans.querycondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class CurrencyQueryVO extends BaseQuestVO{
    private String currencyCode;
    private String currencyName;
    private String currencyEn;
}
