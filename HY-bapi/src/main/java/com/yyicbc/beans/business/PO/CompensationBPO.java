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
@Table(name = "compensation_b", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CompensationBPO extends TableBasePO {
    private static final long serialVersionUID = 32345L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "compensation_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long compensationId;

    @Column(name = "personnel_code", columnDefinition = "varchar(50) COMMENT '人員編碼'")
    private String personnelCode;

    @Column(name = "account_name", columnDefinition = "varchar(200) COMMENT '户名'")
    private String accountName;

    @Column(name = "account_no", columnDefinition = "varchar(50) COMMENT '帐号'")
    private String accountNo;

    @Column(name = "currency_code", columnDefinition = "varchar(20) COMMENT '币种'")
    private String currencyCode;

    @Column(name = "amount_result", columnDefinition = "decimal(31,8) COMMENT '实发合计'")
    private Double amountResult;

    @Column(name = "succeed_status", columnDefinition = "int(11) COMMENT '成功與否: 0失败, 1成功'")
    private Integer succeedStatus;

    @Column(name = "note", columnDefinition = "varchar(200) COMMENT '備註'")
    private String note;

    @Column(name = "error_message", columnDefinition = "varchar(200) COMMENT '错误信息'")
    private String errorMessage;

    @Column(name = "pk_wa_data", columnDefinition = "varchar(20) COMMENT '薪资档案pk'")
    private String pk_wa_data;

    @Column(name = "reserve_field1", columnDefinition = "varchar(100) COMMENT '預留字段1'")
    private String reserveField1;

    @Column(name = "reserve_field2", columnDefinition = "varchar(100) COMMENT '預留字段2'")
    private String reserveField2;

    @Column(name = "reserve_field3", columnDefinition = "varchar(100) COMMENT '預留字段3'")
    private String reserveField3;

    @Column(name = "reserve_field4", columnDefinition = "varchar(100) COMMENT '預留字段4'")
    private String reserveField4;

    @Column(name = "reserve_field5", columnDefinition = "varchar(100) COMMENT '預留字段5'")
    private String reserveField5;

}
