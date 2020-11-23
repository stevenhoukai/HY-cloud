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
@Table(name = "business_type", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BusinessTypePO extends TableBasePO {
    private static final long serialVersionUID = 32345L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "business_type_code", columnDefinition = "varchar(50) COMMENT '业务种类编码'")
    private String businessTypeCode;

    @Column(name = "business_type_name", columnDefinition = "varchar(50) COMMENT '业务种类名称'")
    private String businessTypeName;

    @Column(name = "abbreviation", columnDefinition = "varchar(50) COMMENT '业务简称'")
    private String abbreviation;

    @Column(name = "transaction_code", columnDefinition = "varchar(50) NOT NULL DEFAULT '' COMMENT '交易代码'")
    private String transactionCode;

    @Column(name = "transaction_name", columnDefinition = "varchar(50) NOT NULL DEFAULT '' COMMENT '交易代码名称'")
    private String transactionName;
}
