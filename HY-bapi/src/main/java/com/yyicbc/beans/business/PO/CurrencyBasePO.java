package com.yyicbc.beans.business.PO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "currency_base", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CurrencyBasePO extends TableBasePO{

    private static final long serialVersionUID = 1727297002152327996L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "currency_code", columnDefinition = "varchar(50) COMMENT '币种编码'")
    private String currencyCode;

    @Column(name = "currency_name", columnDefinition = "varchar(50) COMMENT '币种名称'")
    private String currencyName;

    @Column(name = "currency_en", columnDefinition = "varchar(50) COMMENT '币种英文编码'")
    private String currencyEn;

    @Column(name = "status", columnDefinition = "tinyint(1) COMMENT '启用状态 1 启用 0 禁用'")
    private Integer status;

}
