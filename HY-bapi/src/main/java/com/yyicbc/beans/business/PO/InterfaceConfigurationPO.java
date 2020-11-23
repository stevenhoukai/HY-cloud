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
@Table(name = "interface_configuration", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class InterfaceConfigurationPO extends TableBasePO {
    private static final long serialVersionUID = -5522208178518189931L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "config_code", columnDefinition = "varchar(100) COMMENT '配置编码'")
    private String configCode;

    @Column(name = "config_name", columnDefinition = "varchar(50) COMMENT '配置名稱'")
    private String configName;

    @Column(name = "url", columnDefinition = "varchar(50) COMMENT '接口地址'")
    private String url;

    @Column(name = "userinfo_required", columnDefinition = "tinyint(2) COMMENT '是否需要用户名密码 1是 0 否'")
    private String userinfoRequired;

    @Column(name = "username", columnDefinition = "varchar(50) COMMENT '用户名'")
    private String username;

    @Column(name = "password", columnDefinition = "varchar(100) NULL DEFAULT NULL COMMENT '密码'")
    private String password;

    @Column(name = "remark", columnDefinition = "varchar(255) NULL DEFAULT NULL COMMENT '备注'")
    private String remark;

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

    @Column(name = "reserve_field6", columnDefinition = "varchar(100) COMMENT '預留字段6'")
    private String reserveField6;

    @Column(name = "reserve_field7", columnDefinition = "varchar(100) COMMENT '預留字段7'")
    private String reserveField7;

    @Column(name = "reserve_field8", columnDefinition = "varchar(100) COMMENT '預留字段8'")
    private String reserveField8;

    @Column(name = "reserve_field9", columnDefinition = "varchar(100) COMMENT '預留字段9'")
    private String reserveField9;

    @Column(name = "reserve_field10", columnDefinition = "varchar(100) COMMENT '預留字段10'")
    private String reserveField10;

}
