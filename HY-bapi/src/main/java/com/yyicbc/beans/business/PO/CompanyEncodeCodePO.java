package com.yyicbc.beans.business.PO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "company_encode_code", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CompanyEncodeCodePO extends TableBasePO {
    private static final long serialVersionUID = 42345L;

    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "company_encode", columnDefinition = "varchar(50) COMMENT '公司编码'")
    private String companyEncode;

    @Column(name = "company_code", columnDefinition = "varchar(50) COMMENT '公司代码'")
    private String companyCode;
}
