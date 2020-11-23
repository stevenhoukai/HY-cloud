

package com.yyicbc.beans.logmanager;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 *
 * @author steven
 */
@Data
@Table(name = "sys_log")
@Entity //实体注解
@NoArgsConstructor //无参构造
@AllArgsConstructor //全参构造
@Accessors(chain = true) //链式编程
public class SysLogVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 日志记录ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long logId;

	/**
	 * 操作时间
	 */
	@CreationTimestamp
	@Column(name="handle_time")
	private Date handleTime;

	/**
	 * 操作模块(节点)
	 *         {id:'0',name:'币种'},
	 *         {id:'1',name:'业务种类'},
	 *         {id:'2',name:'公司档案信息'},
	 *         {id:'3',name:'公司协议档案信息'},
	 *         {id:'4',name:'公司客户档案信息'},
	 *         {id:'5',name:'模板格式字段档案'},
	 *         {id:'6',name:'Txt模板管理'},
	 *         {id:'7',name:'Excel模板管理'},
	 *         {id:'8',name:'Pdf模板管理'},
	 *         {id:'9',name:'客户文件导入'},
	 *         {id:'10',name:'代发薪资'},
	 *         {id:'11',name:'用户管理'},
	 *         {id:'12',name:'权限管理'},
	 *         {id:'13',name:'定时任务管理'},
	 *         {id:'14',name:'接口配置管理'},
	 */
	@Column(name="module_name")
	private Integer moduleName;

	/**
	 * 操作用户编码
	 */
	@Column(name="handle_usercode",columnDefinition = "varchar(100) COMMENT '操作用户编码'" )
	private String handleUsercode;


	/**
	 * 操作用户名称
	 */
	@Column(name="handle_username",columnDefinition = "varchar(100) COMMENT '操作用户名称'" )
	private String handleUsername;


	/**
	 * 0：新增   1：修改   2：删除
	 */
	@Column(name="handle_type")
	private Integer handleType;

	/**
	 * 操作对象
	 */
	@Column(name="handle_object",columnDefinition = "varchar(1000) COMMENT '操作对象'" )
	private String handleObject;

	/**
	 * 操作备注
	 */
	@Column(name="handle_memo",columnDefinition = "varchar(3000) COMMENT '操作备注'" )
	private String handleMemo;

	/**
	 * 创建时间
	 */
	@CreationTimestamp
	@Column(name="create_time")
	private Date createTime;


	/**
	 * 更新时间
	 */
	@UpdateTimestamp
	@Column(name="update_time")
	private Date updateTime;

}
