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
@Table(name = "txt_template", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TxtTemplatePO extends TableBasePO {
    
    private static final long serialVersionUID = -7555798912717175524L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "txt_template_code", columnDefinition = "varchar(50) COMMENT 'Txt模板编码'")
    private String txtTemplateCode;

    @Column(name = "txt_template_name", columnDefinition = "varchar(50) COMMENT 'Txt模板名称'")
    private String txtTemplateName;

    @Column(name = "txt_template_type", columnDefinition = "varchar(50) COMMENT '模板类别'")
    private String templateType;


}
