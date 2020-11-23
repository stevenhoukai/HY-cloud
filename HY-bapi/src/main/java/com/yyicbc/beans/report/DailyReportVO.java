

package com.yyicbc.beans.report;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 日报
 *
 * @author steven
 */
@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor //全参构造
@Accessors(chain = true) //链式编程
public class DailyReportVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String autopay_date ;
	private String batch_no ;
	private String com_code ;
	private String icbc_ac_no ;
	private Double txn_amt;
	private Double rec_cnt ;
	private String txn_ccy ;
	private String direction ;
	private String drcr_flag ;
	private String maker ;
	private String checker ;
	private String id;
	private String updatestatus;
}
