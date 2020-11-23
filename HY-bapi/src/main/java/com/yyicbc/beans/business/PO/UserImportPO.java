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
@Table(name = "agency_customer_import_file", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserImportPO extends TableBasePO {
    private static final long serialVersionUID = 2342345L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "company_encode", columnDefinition = "varchar(50) COMMENT '公司编码'")
    private String companyEncode;

    @Column(name = "company_code", columnDefinition = "varchar(50) COMMENT '公司代码'")
    private String companyCode;

    @Column(name = "main_agreement_number", columnDefinition = "varchar(50) COMMENT '主协议号'")
    private String mainAgreementNumber;

    @Column(name = "sub_agreement_number", columnDefinition = "varchar(50) COMMENT '子协议号'")
    private String subAgreementNumber;

    @Column(name = "import_file_name", columnDefinition = "varchar(100) COMMENT '导入文件名'")
    private String importFileName;

    @Column(name = "file_status", columnDefinition = "varchar(50) COMMENT '上传文件状态 0 上传成功，1 已审核， 2 已提交FOWA，3 FOWA上传失败，4 FOWA处理成功，5 已弃审'")
    private String fileStatus;

    @Column(name = "total_amount", columnDefinition = "varchar(100) COMMENT '上传文件金额合合计'")
    private String totalAmount;

    @Column(name = "total_count", columnDefinition = "varchar(50) COMMENT '总上传条数'")
    private String totalCount;

    @Column(name = "upload_file_name", columnDefinition = "varchar(50) COMMENT '上传fova文件名'")
    private String uploadFileName;

    //YYYYMMDD，大于等于当前系统日期
    @Column(name = "fova_update_trandate", columnDefinition = "varchar(8) COMMENT '入帐日期'")
    private String tranDate;

    //每个客户的报盘文件中,所有记录的起息日必须一致
    //YYYYMMDD，大于系统日期为顺起息，小于系统日期为倒起息
    @Column(name = "fova_update_valuedat", columnDefinition = "varchar(8) COMMENT '起息日'")
    private String valueDat;

    @Column(name = "deduction_date", columnDefinition = "varchar(8) COMMENT '扣款日'")
    private String deductionDate;

    @Column(name = "pay_account", columnDefinition = "varchar(50) COMMENT '借贷账号'")
    private String payAccount;

    @Column(name = "flow_number", columnDefinition = "varchar(50) COMMENT '公司每日导入流水号'")
    private String flowNumber;

    @Column(name = "run_number", columnDefinition = "varchar(2) COMMENT '跑批场次'")
    private String runNumber;

    @Column(name = "fova_update_sendDate", columnDefinition = "varchar(8) COMMENT '发送日期'")
    private String sendDate;

    @Column(name = "error_content", columnDefinition = "varchar(50) COMMENT '错误原因'")
    private String errorContent;

    @Column(name = "maker_id", columnDefinition = "bigint(20) DEFAULT 0 COMMENT '经办人ID'")
    private Long makerId;

    @Column(name = "checker_id", columnDefinition = "bigint(20) DEFAULT 0 COMMENT '复核人ID'")
    private Long checkerId;

    /*add by fmm 财政局汇导入TXT格式的最后一行，记录到这个字段，导出时候有用*/
    @Column(name = "dsf_total", columnDefinition = "varchar(200) COMMENT '财政局汇总记录'")
    private String dsfTotal;

    @Column(name = "dsf_add", columnDefinition = "varchar(1) COMMENT '财政局是否补号标识Y/N 默认N'")
    private String dsfAdd = "N";
}