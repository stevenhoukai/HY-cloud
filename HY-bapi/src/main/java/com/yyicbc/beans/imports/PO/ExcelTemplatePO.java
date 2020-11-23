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
@Table(name = "excel_template", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ExcelTemplatePO extends TableBasePO {

    private static final long serialVersionUID = -7454997762971526646L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "excel_template_code", columnDefinition = "varchar(50) COMMENT 'Excel模板编码'")
    private String excelTemplateCode;

    @Column(name = "excel_template_name", columnDefinition = "varchar(50) COMMENT 'Excel模板名称'")
    private String excelTemplateName;

    @Column(name = "excel_template_type", columnDefinition = "varchar(50) COMMENT '模板类别'")
    private String templateType;


}
