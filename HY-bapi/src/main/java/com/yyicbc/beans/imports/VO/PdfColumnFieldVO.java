package com.yyicbc.beans.imports.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdfColumnFieldVO implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String pdfTemplateCode;
    private String formatFieldCode;
    private String columnNumber;
    private String fieldLength;
}
