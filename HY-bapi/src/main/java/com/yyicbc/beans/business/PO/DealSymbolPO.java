package com.yyicbc.beans.business.PO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;


@EqualsAndHashCode(callSuper=true)
@Data
@Table(name = "deal_symbol",uniqueConstraints=@UniqueConstraint(columnNames="symbol_code"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DealSymbolPO extends TableBasePO 
{
    private static final long serialVersionUID = -5732307973962666317L;
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol_code", columnDefinition = "varchar(100) COMMENT '标志编码'")
    private String symbolCode;

    @Column(name = "symbol_name", columnDefinition = "varchar(50) COMMENT '标志名稱'")
    private String symbolName;

    @Column(name = "fova_symbol_name", columnDefinition = "varchar(50) COMMENT 'FOVA 处理标志名稱'")
    private String fovaSymbolName;

}
