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
@Table(name = "agency_company_info", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CompanyBasePO extends TableBasePO {
    private static final long serialVersionUID = 12345L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "company_encode", columnDefinition = "varchar(100) COMMENT '公司编码'")
    private String companyEncode;

    @Column(name = "company_name", columnDefinition = "varchar(50) COMMENT '公司名称'")
    private String companyName;

    @Column(name = "company_description", columnDefinition = "varchar(100) COMMENT '公司描述'")
    private String companyDescription;

    @Column(name = "drcr_account", columnDefinition = "varchar(100) COMMENT 'Dr/Cr账号'")
    private String drCrAccount;

    @Column(name = "company_phone", columnDefinition = "varchar(100) COMMENT '公司电话'")
    private String companyPhone;

    @Column(name = "personal_phone", columnDefinition = "varchar(100) COMMENT '个人电话'")
    private String personalPhone;

    @Column(name = "fax", columnDefinition = "varchar(50) COMMENT '传真FAX'")
    private String fax;

    @Column(name = "company_status", columnDefinition = "varchar(2) COMMENT '公司状态 0:禁用 1:正常'")
    private String companyStatus;

    @Column(name = "company_contact", columnDefinition = "varchar(100) COMMENT '公司联系人'")
    private String companyContact;

    @Column(name = "company_account", columnDefinition = "varchar(100) COMMENT '公司账号'")
    private String companyAccount;

    @Column(name = "personal_contact", columnDefinition = "varchar(100) COMMENT '个人联络人'")
    private String personalContact;

    @Column(name = "address1", columnDefinition = "varchar(100) COMMENT '地址1'")
    private String address1;

    @Column(name = "account_name", columnDefinition = "varchar(100) COMMENT '账户名'")
    private String accountName;

    @Column(name = "account_name_eng", columnDefinition = "varchar(100) COMMENT '英文账户名'")
    private String accountNameEng;

    @Column(name = "is_required_bill_number", columnDefinition = "tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否账单编号必填'")
    private Integer isRequiredBillNumber;

    @Column(name = "Email1", columnDefinition = "varchar(100) COMMENT 'Email1'")
    private String email1;

    @Column(name = "Email2", columnDefinition = "varchar(100) COMMENT 'Email2'")
    private String email2;

    @Column(name = "file_type", columnDefinition = "varchar(2) COMMENT '导入文件格式 0:TXT 1:EXCEL 2:全部'")
    private String fileType;

    @Column(name = "excel_template_type", columnDefinition = "varchar(100) COMMENT '导入excel模板类型'")
    private String excelTemplateType;

    @Column(name = "txt_template_type", columnDefinition = "varchar(100) COMMENT '导入txt模板类型'")
    private String txtTemplateType;

    @Column(name = "export_file_type", columnDefinition = "varchar(2) COMMENT '导出文件格式 0:TXT 1:EXCEL 2:PDF 3:全部'")
    private String exportFileType;

    @Column(name = "export_excel_template_type", columnDefinition = "varchar(100) COMMENT '导出模板类型'")
    private String exportExcelTemplateType;

    @Column(name = "export_txt_template_type", columnDefinition = "varchar(100) COMMENT '导出模板类型'")
    private String exportTxtTemplateType;

    @Column(name = "export_pdf_template_type", columnDefinition = "varchar(100) COMMENT '导出模板类型'")
    private String exportPdfTemplateType;

    @Column(name = "export_template_minunit", columnDefinition = "varchar(100) COMMENT '最小金额单位1-分；2-角；3-元；4-厘；5-毫'")
    private String exportTemplateMinunit;
}
