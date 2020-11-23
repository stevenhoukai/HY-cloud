

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
public class ProtocalReportVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String companyCode ;
	private String companyName ;
	private String companyAccount ;
	private String mainNumber ;
	private String effectDate;
	private String businessType ;
	private String businessTypename ;
	private String ccy ;
	private String flag ;
	private String status ;
//	private String id;
}
