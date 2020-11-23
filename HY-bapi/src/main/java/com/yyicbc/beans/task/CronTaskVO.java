package com.yyicbc.beans.task;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/***
 * @author  stv
 */
@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor //全参构造
@Accessors(chain = true) //链式编程
public class CronTaskVO implements Serializable {
    private String jobName;
    private String jobClass;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String jobTime;
    private String description;
    private String jobGroupName = "ICBC";
    private Long jobTimes = (long)-1;
    private String[] paramvalues;
    private String[] keys;
}
