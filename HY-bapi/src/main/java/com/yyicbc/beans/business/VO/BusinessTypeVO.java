package com.yyicbc.beans.business.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessTypeVO implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String businessTypeCode;
    private String businessTypeName;
    private String abbreviation;
    private String transactionCode;
    private String transactionName;
}