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
@Table(name = "agency_company_protocal_info", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CompanyAgreementPO extends TableBasePO {
    private static final long serialVersionUID = 22345L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "company_encode", columnDefinition = "varchar(50) COMMENT '公司编码'")
    private String companyEncode;

    @Column(name = "company_name", columnDefinition = "varchar(100) COMMENT '公司名称'")
    private String companyName;

    @Column(name = "company_code", columnDefinition = "varchar(100) COMMENT '公司代码'")
    private String companyCode;

    @Column(name = "company_account", columnDefinition = "varchar(100) COMMENT '公司账号'")
    private String companyAccount;

    @Column(name = "ccy", columnDefinition = "varchar(100) COMMENT 'ccy'")
    private String ccy;

    @Column(name = "search_condition", columnDefinition = "varchar(100) COMMENT '搜索条件 0:账单 1:账号 2:账单+账号'")
    private String searchCondition;

    @Column(name = "billnumber_restriction", columnDefinition = "varchar(50) COMMENT '账单编号补位限制'")
    private String billNumberRestriction;

    @Column(name = "business_type_code", columnDefinition = "varchar(100) COMMENT '业务种类代码'")
    private String businessTypeCode;

    @Column(name = "main_agreement_number", columnDefinition = "varchar(100) COMMENT '主协议号'")
    private String mainAgreementNumber;

    @Column(name = "agreement_status", columnDefinition = "varchar(100) COMMENT '协议状态'")
    private String agreementStatus;

    @Column(name = "pay_limit", columnDefinition = "varchar(100) COMMENT '收费期限'")
    private String payLimit;

    @Column(name = "sign_driect", columnDefinition = "varchar(100) COMMENT '签约方向标志'")
    private String signDriect;

    @Column(name = "pay_sign", columnDefinition = "varchar(100) COMMENT '抄汇标志'")
    private String paySign;

    @Column(name = "product_type", columnDefinition = "varchar(100) COMMENT '产品种类'")
    private String productType;

    @Column(name = "product_index", columnDefinition = "varchar(100) COMMENT '产品序号'")
    private String productIndex;

    @Column(name = "pay_method", columnDefinition = "varchar(100) COMMENT '收付法则'")
    private String paymethod;

    @Column(name = "borrow_sign", columnDefinition = "varchar(100) COMMENT '借贷标志'")
    private String borrowSign;

    @Column(name = "product_type_short", columnDefinition = "varchar(100) COMMENT '代理业务种类简称'")
    private String productTypeShort;

    @Column(name = "charge_sign", columnDefinition = "varchar(100) COMMENT '收费标志'")
    private String chargeSign;

    @Column(name = "charge_type", columnDefinition = "varchar(100) COMMENT '费用种类'")
    private String chargeType;

    @Column(name = "charge_method", columnDefinition = "varchar(100) COMMENT '收费方式'")
    private String chargeMethod;

    @Column(name = "charge_by_number_standard", columnDefinition = "varchar(100) COMMENT '按笔数收费标准'")
    private String chargeByNumberStandard;

    @Column(name = "scalepc", columnDefinition = "varchar(100) COMMENT '按金额收费比例'")
    private String scalepc;

    @Column(name = "agreement_charge_sign", columnDefinition = "varchar(100) COMMENT '签协议收费标志'")
    private String agreementChargeSign;

    @Column(name = "min_amount", columnDefinition = "varchar(100) COMMENT '最低金额'")
    private String minAmount;

    @Column(name = "max_amount", columnDefinition = "varchar(100) COMMENT '最高金额'")
    private String maxAmount;

    @Column(name = "regular_quota_sign", columnDefinition = "varchar(100) COMMENT '定期定额标志'")
    private String regularQuotaSign;

    @Column(name = "trade_code", columnDefinition = "varchar(100) COMMENT '交易代码'")
    private String tradeCode;

    @Column(name = "in_account_position", columnDefinition = "varchar(100) COMMENT '入账网点'")
    private String inAccountPosition;

    @Column(name = "company_sign", columnDefinition = "varchar(100) COMMENT '企业签名标志'")
    private String companySign;

    @Column(name = "agreement_type", columnDefinition = "varchar(100) COMMENT '合约类型'")
    private String agreementType;

    @Column(name = "agent_certificates_type", columnDefinition = "varchar(100) COMMENT '代理人证件类型'")
    private String agentCertificatesType;

    @Column(name = "comments", columnDefinition = "varchar(100) COMMENT '备注'")
    private String comments;

    @Column(name = "abstract_content", columnDefinition = "varchar(100) COMMENT '摘要'")
    private String abstractContent;

    @Column(name = "sign_date", columnDefinition = "varchar(100) COMMENT '签约日期'")
    private String signDate;

    @Column(name = "effect_date", columnDefinition = "varchar(100) COMMENT '生效日期'")
    private String effectDate;

    @Column(name = "end_date", columnDefinition = "varchar(100) COMMENT '终止日期'")
    private String endDate;

    @Column(name = "bill_index", columnDefinition = "varchar(100) COMMENT '申请单编号'")
    private String billIndex;

    @Column(name = "account_index", columnDefinition = "varchar(100) COMMENT '账户序号'")
    private String accountIndex;

    @Column(name = "account_name", columnDefinition = "varchar(100) COMMENT '账户名称'")
    private String accountName;

    @Column(name = "account_name_eng", columnDefinition = "varchar(100) COMMENT '英文户名'")
    private String accountNameEng;

    @Column(name = "customer_index", columnDefinition = "varchar(100) COMMENT '客户序号'")
    private String customerIndex;

    @Column(name = "order_index", columnDefinition = "varchar(100) COMMENT '申请单编号'")
    private String orderIndex;

    @Column(name = "old_account", columnDefinition = "varchar(5) COMMENT '是否新旧账号1旧2新'")
    private String oldAccount;
}
