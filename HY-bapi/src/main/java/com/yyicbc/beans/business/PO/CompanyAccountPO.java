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
@Table(name = "company_account", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CompanyAccountPO extends TableBasePO {
    private static final long serialVersionUID = 32345L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "company_account", columnDefinition = "varchar(50) COMMENT '公司账号'")
    private String companyAccount;

    @Column(name = "ccy_code", columnDefinition = "varchar(50) COMMENT '币种'")
    private String ccy;

    @Column(name = "business_type_code", columnDefinition = "varchar(50) COMMENT '业务种类编码'")
    private String businessTypeCode;

    @Column(name = "company_encode", columnDefinition = "varchar(50) COMMENT '公司编码'")
    private String companyEncode;
}
