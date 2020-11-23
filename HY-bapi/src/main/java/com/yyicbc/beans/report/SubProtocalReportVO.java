

package com.yyicbc.beans.report;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 日报
 *
 * @author steven
 */
@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor //全参构造
@Accessors(chain = true) //链式编程
public class SubProtocalReportVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String company_code ;
	private String trade_code ;
	private String account_name ;
	private String fowa_account ;
	private String sub_agreement_number;
	private String main_agreement_number ;
	private String ccy ;
	private String customer_status ;
	private String generate_date ;
	private String modify_time ;
	private String cancel_date ;
	private String report_time ;
	private String beneficiary_id ;
	private String reference_code ;
	private String reference_transaction ;
	private String bill_number ;
	private String comments ;
	private String product_index ;
	private String stop_amount_per ;

}
