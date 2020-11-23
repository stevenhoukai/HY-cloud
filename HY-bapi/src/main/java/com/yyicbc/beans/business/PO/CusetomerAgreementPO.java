package com.yyicbc.beans.business.PO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "agency_company_customer_info", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CusetomerAgreementPO extends TableBasePO {
    private static final long serialVersionUID = 52345L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "company_code", columnDefinition = "varchar(50) COMMENT '公司代码'")
    private String companyCode;

    @Column(name = "company_account", columnDefinition = "varchar(100) COMMENT '公司账号'")
    private String companyAccount;

    @Column(name = "bill_number", columnDefinition = "varchar(100) COMMENT '账单编号'")
    private String billNumber;

    @Column(name = "fowa_account", columnDefinition = "varchar(100) COMMENT 'fowa账号'")
    private String fowaAccount;

    @Column(name = "customer_name", columnDefinition = "varchar(100) COMMENT '客户名称'")
    private String customerName;

    @Column(name = "account_owner_id", columnDefinition = "varchar(100) COMMENT '账户拥有者ID'")
    private String accountOwnerId;

    @Column(name = "beneficiary_id", columnDefinition = "varchar(100) COMMENT '收益人ID'")
    private String beneficiaryId;

    @Column(name = "main_agreement_number", columnDefinition = "varchar(100) COMMENT '主协议号'")
    private String mainAgreementNumber;

    @Column(name = "order_index", columnDefinition = "varchar(100) COMMENT '申请单编号'")
    private String orderIndex;

    @Column(name = "sub_agreement_number", columnDefinition = "varchar(100) COMMENT '子协议号'")
    private String subAgreementNumber;

    @Column(name = "reference_transaction_number", columnDefinition = "varchar(100) COMMENT '参考交易号'")
    private String referenceTransactionNumber;

    @Column(name = "reference_code", columnDefinition = "varchar(100) COMMENT '参考编号'")
    private String referenceCode;

    @Column(name = "reference_transaction", columnDefinition = "char(1) COMMENT '参考交易'")
    private String referenceTransaction;

    @Column(name = "reference_transaction_end", columnDefinition = "char(5) COMMENT '参考交易_前后台'")
    private String referenceTransactionEnd;

    @Column(name = "reference_transaction_net", columnDefinition = "char(5) COMMENT '参考交易_网上'")
    private String referenceTransactionNet;

    @Column(name = "reference_transaction_txt", columnDefinition = "char(5) COMMENT '参考交易_正本'")
    private String referenceTransactionTxt;

    @Column(name = "customer_status", columnDefinition = "varchar(100) COMMENT '客户协议状态'")
    private String customerStatus;

    @Column(name = "scalepc", columnDefinition = "varchar(10) COMMENT '按金额收费比例'")
    private String scalepc;

    @Column(name = "service_status", columnDefinition = "varchar(100) default '0' COMMENT '服务状态'")
    private String serviceStatus ="0";//默认新增状态

    @Column(name = "cancelDate", columnDefinition = "timestamp default CURRENT_TIMESTAMP COMMENT '取消日期'")
    private Date cancelDate;

    @Column(name = "reportTime", columnDefinition = "timestamp default CURRENT_TIMESTAMP COMMENT '报表日期'")
    private Date reportTime;

    @Column(name = "generateDate", columnDefinition = "timestamp default CURRENT_TIMESTAMP COMMENT '生成日期'")
    private Date generateDate = new Date();

    @Column(name = "modifyTime", columnDefinition = "timestamp default CURRENT_TIMESTAMP COMMENT '修改日期'")
    private Date modifyTime;

    @Column(name = "address1", columnDefinition = "varchar(100) COMMENT '地址1'")
    private String address1;

    @Column(name = "beneficiary_name", columnDefinition = "varchar(100) COMMENT '收益人名称'")
    private String beneficiaryName;

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

    @Column(name = "agreement_charge_sign", columnDefinition = "varchar(100) COMMENT '签协议收费标志'")
    private String agreementChargeSign;

    @Column(name = "max_amount", columnDefinition = "varchar(100) COMMENT '最高金额'")
    private String maxAmount;

    @Column(name = "min_amount", columnDefinition = "varchar(100) COMMENT '最低金额'")
    private String minAmount;

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

    @Column(name = "start_account_name", columnDefinition = "varchar(100) COMMENT '发起方户名'")
    private String startAccountName;

    @Column(name = "customer_index", columnDefinition = "varchar(100) COMMENT '客户编码'")
    private String customerIndex;

    @Column(name = "account_name", columnDefinition = "varchar(100) COMMENT '户名'")
    private String accountName;

    @Column(name = "account_name_eng", columnDefinition = "varchar(100) COMMENT '英文户名'")
    private String accountNameEng;

    @Column(name = "account_index", columnDefinition = "varchar(100) COMMENT '账户序号'")
    private String accountIndex;

    @Column(name = "ccy", columnDefinition = "varchar(100) COMMENT 'ccy'")
    private String ccy;

    @Column(name = "stopCCY", columnDefinition = "varchar(100) COMMENT '止付币种'")
    private String stopCCY;

    @Column(name = "stop_amount_per", columnDefinition = "varchar(100) COMMENT '单次止付金额'")
    private String stopAmountPer;

    @Column(name = "stop_amount_month", columnDefinition = "varchar(100) COMMENT '每月止付金额'")
    private String stopAmountMonth;

    @Column(name = "old_account", columnDefinition = "varchar(5) COMMENT '是否新旧账号1旧2新'")
    private String oldAccount;
}
