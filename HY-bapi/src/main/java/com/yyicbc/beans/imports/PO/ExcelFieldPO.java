package com.yyicbc.beans.imports.PO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yyicbc.beans.business.PO.TableBasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "excel_field_info", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ExcelFieldPO extends TableBasePO {

    private static final long serialVersionUID = 3168909734277561788L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "format_field_code", columnDefinition = "varchar(50) COMMENT '导入格式编码'")
    private String formatFieldCode;

    @Column(name = "excel_template_code", columnDefinition = "varchar(50) COMMENT 'Excel模板编码'")
    private String excelTemplateCode;

    @Column(name = "column_number", columnDefinition = "varchar(50) COMMENT '列编号'")
    private String columnNumber;

}
