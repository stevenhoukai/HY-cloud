package com.yyicbc.beans.imports.PO;

import com.yyicbc.beans.business.PO.TableBasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @ClassNameDsfAccnoPO
 * @Description 财政局 账号中间表
 * @Author vic
 * @Date2020/3/10 16:09
 * @Version V1.0
 **/

@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "nr0103_ins_1d", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DsfAccnoPO extends TableBasePO {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "datadate", columnDefinition = "varchar(8) COMMENT 'datadate'")
    private String datadate;
    @Column(name = "name", columnDefinition = "varchar(500) COMMENT 'name'")
    private String name;
    @Column(name = "accno", columnDefinition = "varchar(34) COMMENT 'accno'")
    private String accno;
    @Column(name = "maccno", columnDefinition = "varchar(34) COMMENT 'maccno'")
    private String maccno;



}
