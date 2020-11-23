package com.yyicbc.beans.imports.PO;

import com.yyicbc.beans.business.PO.TableBasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @ClassNameGenerateImportPO
 * @Description 退税及给付金声明书专栏导入
 * @Author vic
 * @Date2020/4/3 14:59
 * @Version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "generate_import", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GenerateImportPO extends TableBasePO {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "generdate", columnDefinition = "varchar(8) COMMENT '调用接口日期'")
    private String generdate;

    @Column(name = "sequence", columnDefinition = "varchar(50) COMMENT 'sequence'")
    private String sequence;

    @Column(name = "field1", columnDefinition = "varchar(4) COMMENT '声明书编号(年份)'")
    private String field1;

    @Column(name = "field2", columnDefinition = "varchar(14) COMMENT '声明书编号(序号)'")
    private String field2;

    @Column(name = "field3", columnDefinition = "varchar(2) COMMENT '声明书编号(子序号)'")
    private String field3;

    @Column(name = "field4", columnDefinition = "varchar(20) COMMENT '申明书类型'")
    private String field4;

    @Column(name = "field5", columnDefinition = "varchar(20) COMMENT '申请人身份证明文件类型'")
    private String field5;

    @Column(name = "field6", columnDefinition = "varchar(20) COMMENT '申请人身份证明文件编号'")
    private String field6;

    @Column(name = "field7", columnDefinition = "varchar(10) COMMENT '申请人之出生日期(YYYYMMDD)'")
    private String field7;

    @Column(name = "field8", columnDefinition = "varchar(20) COMMENT '申请人之性别'")
    private String field8;

    @Column(name = "field9", columnDefinition = "varchar(30) COMMENT '申请人之银行账号'")
    private String field9;

    @Column(name = "field10", columnDefinition = "varchar(8) COMMENT '申请人之本澳流动电话'")
    private String field10;

    @Column(name = "field11", columnDefinition = "varchar(30) COMMENT '申请人之电邮地址'")
    private String field11;

    @Column(name = "field12", columnDefinition = "varchar(20) COMMENT '申请人是否同意接收中文讯息'")
    private String field12;

    @Column(name = "field13", columnDefinition = "varchar(20) COMMENT '申请人是否同意接收葡文讯息'")
    private String field13;

    @Column(name = "field14", columnDefinition = "varchar(20) COMMENT '申请人是否同意接收英文讯息'")
    private String field14;

    @Column(name = "field15", columnDefinition = "varchar(8) COMMENT '申明书申请日期(YYYYMMDD)'")
    private String field15;

    @Column(name = "field16", columnDefinition = "varchar(6) COMMENT '申明书申请时间(hhmmss)'")
    private String field16;

    @Column(name = "field17", columnDefinition = "varchar(40) COMMENT '财政局预留栏1'")
    private String field17;

    @Column(name = "field18", columnDefinition = "varchar(40) COMMENT '财政局预留栏2'")
    private String field18;

    @Column(name = "field19", columnDefinition = "varchar(40) COMMENT '银行内部参考用栏'")
    private String field19;

    @Column(name = "field20", columnDefinition = "varchar(20) COMMENT '申明书生成日期时间(YYYYMMDDhhmmss)'")
    private String field20;

    @Column(name = "field21", columnDefinition = "varchar(15) COMMENT 'CIF'")
    private String field21;

    @Column(name = "field22", columnDefinition = "varchar(20) COMMENT '登记渠道'")
    private String field22;

    @Column(name = "field23", columnDefinition = "varchar(20) COMMENT '客户姓名'")
    private String field23;

    @Column(name = "field24", columnDefinition = "varchar(10) COMMENT '生日'")
    private String field24;

    @Column(name = "field25", columnDefinition = "varchar(10) COMMENT '证件类型'")
    private String field25;

    @Column(name = "field26", columnDefinition = "varchar(10) COMMENT '国籍'")
    private String field26;

    @Column(name = "field27", columnDefinition = "varchar(99) COMMENT '申请人之葡文姓名'")
    private String field27;

    @Column(name = "field28", columnDefinition = "varchar(99) COMMENT '申请人之中文姓名'")
    private String field28;


}
