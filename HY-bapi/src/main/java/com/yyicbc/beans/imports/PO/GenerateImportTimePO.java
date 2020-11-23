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
 * @Description 退税及给付金声明书专栏导入 时间记录，
 * @Author vic
 * @Date2020/4/3 14:59
 * @Version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "generate_import_time", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GenerateImportTimePO extends TableBasePO {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "generdate", columnDefinition = "varchar(8) COMMENT '调用接口日期'")
    private String generdate;

    @Column(name = "genertime", columnDefinition = "varchar(6) COMMENT '调用接口时间'")
    private String genertime;

}
