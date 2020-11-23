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
@Table(name = "pdf_template", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PdfTemplatePO extends TableBasePO {

    private static final long serialVersionUID = -7555798912717175524L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "pdf_template_code", columnDefinition = "varchar(50) COMMENT 'Pdf模板编码'")
    private String pdfTemplateCode;

    @Column(name = "pdf_template_name", columnDefinition = "varchar(50) COMMENT 'Pdf模板名称'")
    private String pdfTemplateName;

    @Column(name = "pdf_template_type", columnDefinition = "varchar(50) COMMENT '模板类别'")
    private String templateType;


}
