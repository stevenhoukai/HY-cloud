package com.yyicbc.beans.business.PO;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper=false)
public class TableBasePO implements Serializable {

//    id              主键自增长
//    code         编码
//    name        名称
//    create_time    创建时间
//    create_user_id 创建人id
//    update_time  更新时间
//    update_user_id  更新人id
//    deptid      部门id

    @Column(name = "create_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP COMMENT '创建时间'")
    private Date createTime = new Date();

    @Column(name = "update_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'")
    private Date updateTime = new Date();

    @Column(name = "dr", columnDefinition = "int default 0 COMMENT '软删除位 0:正常 1:删除'")
    private int dr=0;

    @Column(name = "comment", columnDefinition = "varchar(50) COMMENT '备注'")
    private String comment ="";
}
