package com.yyicbc.beans.business.PO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 *@author vic fu
 * 回传中间表
 * 后台任务调用ICBC提供的接口，保存到这个中间表（记录发送日期不等于交易日期的数据）
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "fova_return_temp_t", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FovaReturnTempPO  extends TableBasePO {

    private static final long serialVersionUID = 1928297002152327996L;
    @Id
    @Column(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_num", columnDefinition = "int(11) NOT NULL COMMENT '行号'")
    private int row_num;

    @Column(name = "agtsname", columnDefinition = "nvarchar(12) COMMENT 'agtsname'")
    private String agtsname;

    @Column(name = "batchno", columnDefinition = "varchar(17) COMMENT 'batchno'")
    private String batchno;

    @Column(name = "procnum", columnDefinition = "varchar(7) COMMENT 'procnum'")
    private String procnum;

    @Column(name = "drcrf", columnDefinition = "nvarchar(2) NOT NULL COMMENT 'drcrf'")
    private String drcrf;

    @Column(name = "sendate", columnDefinition = "nvarchar(10) COMMENT 'sendate'")
    private String sendate;

    @Column(name = "trandate", columnDefinition = "nvarchar(10) COMMENT 'trandate'")
    private String trandate;

    @Column(name = "valuedat", columnDefinition = "nvarchar(10) COMMENT 'valuedat'")
    private String valuedat;

    @Column(name = "tracurr", columnDefinition = "decimal(3, 0) COMMENT 'tracurr'")
    private String tracurr;

    @Column(name = "amount", columnDefinition = "decimal(24, 6) COMMENT 'amount'")
    private Double amount;

    @Column(name = "status", columnDefinition = "varchar(3) COMMENT 'status'")
    private String status;

    @Column(name = "recordf", columnDefinition = "varchar(3) NOT NULL COMMENT 'recordf'")
    private String recordf;

    @Column(name = "mediumid", columnDefinition = "varchar(34) COMMENT 'mediumid'")
    private String mediumid;

    @Column(name = "cashexf", columnDefinition = "varchar(1) COMMENT 'cashexf'")
    private String cashexf;

    @Column(name = "custno", columnDefinition = "nvarchar(20) COMMENT 'custno'")
    private String custno;

    @Column(name = "accname", columnDefinition = "nvarchar(750) COMMENT 'accname'")
    private String accname;

    @Column(name = "agenpsn", columnDefinition = "nvarchar(34) COMMENT 'agenpsn'")
    private String agenpsn;

    /*@Column(name = "update_status", columnDefinition = "nvarchar(34) COMMENT '已更新关联表数据状态：0 未更新 1已更新'")
    private int updateStatus;*/
}
