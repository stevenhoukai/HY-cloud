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
@Table(name = "format_field_info", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FormatFieldPO extends TableBasePO {

    private static final long serialVersionUID = 6471002877117831420L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "format_field_code", columnDefinition = "varchar(50) COMMENT '导入格式编码'")
    private String formatFieldCode;

    @Column(name = "format_field_name", columnDefinition = "varchar(50) COMMENT '导入格式名称'")
    private String formatFieldName;

    @Column(name = "format_field_type", columnDefinition = "varchar(50) COMMENT '导入格式类型'")
    private String formatFieldType;

    @Column(name = "fova_field_code", columnDefinition = "varchar(50) COMMENT 'fova对应字段'")
    private String fovaFieldCode;

    @Column(name = "is_edit", columnDefinition = "tinyint(1) COMMENT '是否可编辑  1是 0否'")
    private Integer isEdit;


}
