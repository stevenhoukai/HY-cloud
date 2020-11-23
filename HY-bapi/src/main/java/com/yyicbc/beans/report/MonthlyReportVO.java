

package com.yyicbc.beans.report;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 月报
 *
 * @author steven
 */
@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor //全参构造
@Accessors(chain = true) //链式编程
public class MonthlyReportVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String autopay_date ;
	private String company_txn_type ;
	private String company_dr_cr_ac ;
	private String company_desc ;
	private String txn_ccy ;
	private Double txn_amt;
	private Double rec_cnt ;

}
