package com.yyicbc.beans.business.PO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;


@EqualsAndHashCode(callSuper=true)
@Data
@Table(name = "compensation", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CompensationPO extends TableBasePO {
    private static final long serialVersionUID = 32345L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", columnDefinition = "int(11) COMMENT '序号'")
    private Integer serialNumber;

    @Column(name = "company_no", columnDefinition = "varchar(50) COMMENT '公司編碼'")
    private String companyNo;

    @Column(name = "company_name", columnDefinition = "varchar(200) COMMENT '公司名稱'")
    private String companyName;

    @Column(name = "run_batch", columnDefinition = "varchar(50) COMMENT '跑批批次'")
    private String runBatch;

    @Column(name = "upload_date", columnDefinition = "timestamp NULL DEFAULT NULL COMMENT '上傳日期'")
    private Date uploadDate;

    @Column(name = "total_number", columnDefinition = "int(11) COMMENT '交易總筆數'")
    private Integer totalNumber;

    @Column(name = "total_amount", columnDefinition = "decimal(31,8) COMMENT '交易總金額'")
    private Double totalAmount;

    @Column(name = "deduct_date", columnDefinition = "timestamp NULL DEFAULT NULL COMMENT '扣數日'")
    private Date deductDate;

    @Column(name = "booked_date", columnDefinition = "timestamp NULL DEFAULT NULL COMMENT '入賬日期'")
    private Date bookedDate;

    @Column(name = "batch_status", columnDefinition = "int(11) COMMENT '跑批狀態: 0失败, 1成功, 2待跑批, 3跑批中, 4已执行, 5已弃审 '")
    private Integer batchStatus;

    @Column(name = "error_content", columnDefinition = "varchar(200) COMMENT '错误原因'")
    private String errorContent;

    @Column(name = "note", columnDefinition = "varchar(200) COMMENT '備註'")
    private String note;

    @Column(name = "guid", columnDefinition = "varchar(200) COMMENT '唯一標識'")
    private String guid;

    @Column(name = "pk_payroll", columnDefinition = "varchar(20) COMMENT '薪资审批pk'")
    private String pk_payroll;

    @Column(name = "send_status", columnDefinition = "int(11) DEFAULT 0 COMMENT '反写状态:0失败,1成功'")
    private Integer sendStatus;

    @Column(name = "company_account_no", columnDefinition = "varchar(50) COMMENT '公司帳號'")
    private String companyAccountNo;

    @Column(name = "interest_date", columnDefinition = "timestamp NULL DEFAULT NULL COMMENT '啟息日期'")
    private Date interestDate;

    @Column(name = "distribute_times", columnDefinition = "varchar(50) COMMENT '發放批次'")
    private String distributeTimes;

    @Column(name = "upload_file_name", columnDefinition = "varchar(50) COMMENT '上传fova文件名'")
    private String uploadFileName;

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

    @Column(name = "vdef1", columnDefinition = "varchar(101) COMMENT '自定義字段1'")
    private String vdef1;

    @Column(name = "vdef2", columnDefinition = "varchar(101) COMMENT '自定義字段2'")
    private String vdef2;

    @Column(name = "vdef3", columnDefinition = "varchar(101) COMMENT '自定義字段3'")
    private String vdef3;

    @Column(name = "vdef4", columnDefinition = "varchar(101) COMMENT '自定義字段4'")
    private String vdef4;

    @Column(name = "vdef5", columnDefinition = "varchar(101) COMMENT '自定義字段5'")
    private String vdef5;
}
