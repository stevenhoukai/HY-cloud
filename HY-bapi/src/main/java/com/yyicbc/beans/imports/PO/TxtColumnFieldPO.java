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
@Table(name = "txt_column_field", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TxtColumnFieldPO extends TableBasePO {

    private static final long serialVersionUID = -4570202123500507279L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "txt_template_code", columnDefinition = "varchar(50) COMMENT 'Txt模板编码'")
    private String txtTemplateCode;

    @Column(name = "format_field_code", columnDefinition = "varchar(50) COMMENT '导入格式编码'")
    private String formatFieldCode;

    @Column(name = "column_number", columnDefinition = "varchar(50) COMMENT '列号'")
    private String columnNumber;

    @Column(name = "field_length", columnDefinition = "varchar(50) COMMENT '字段长度'")
    private String fieldLength;


}
