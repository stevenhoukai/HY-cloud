

package com.yyicbc.beans.security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 *
 * @author steven
 */
@Data
@Table(name = "sys_user",uniqueConstraints=@UniqueConstraint(columnNames="user_code"))
@Entity //实体注解
@NoArgsConstructor //无参构造
@AllArgsConstructor //全参构造
@Accessors(chain = true) //链式编程
public class SysUserVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long userId;

	/**
	 * 用户编码
	 */
//	@NotBlank(message="用户编码不能为空")
	@Column(name="user_code",columnDefinition = "varchar(50) COMMENT '用户编码'" )
	private String userCode;

	/**
	 * 用户名
	 */
//	@NotBlank(message="用户名不能为空")
	@Column(name="user_name",columnDefinition = "varchar(100) COMMENT '用户名称'" )
	private String userName;

	/**
	 * 部门
	 */
	@Column(name="user_dept",columnDefinition = "varchar(100) COMMENT '用户部门'" )
	private String userDept;

	/**
	 * 密码
	 */
//	@NotBlank(message="密码不能为空")
	@Column(name="password",columnDefinition = "varchar(100) COMMENT '用户密码'" )
	private String password;

	/**
	 * 盐
	 */
	@Column(name="salt")
	private String salt = "1234567890";

	/**
	 * 邮箱
	 */
//	@NotBlank(message="邮箱不能为空" )
	@Email(message="邮箱格式不正确")
	@Column(name="email",columnDefinition = "varchar(100) COMMENT '用户邮箱'" )
	private String email;





	/**
	 * 手机号
	 */
	@Column(name="mobile",columnDefinition = "varchar(20) COMMENT '用户手机'" )
	private String mobile;

	/**
	 * 状态  0：禁用   1：启用
	 */
	@Column(name="status" )
	private Integer status = 1;

	/**
	 * 角色状态状态  0：all   1：target
	 */
	@Transient
	private Integer rolestatus = 1;

	/**
	 * 状态  0：业务员   1：管理员    2：业务主管
	 */
	@Column(name="usertype" )
	private Integer usertype = 0;

	/**
	 * 状态  1：男   1：女
	 */
	@Column(name="sex" )
	private Integer sex = 0;

	/**
	 * 角色ID列表
	 */
	@Transient
	private List<Long> roleIdList;

	/**
	 * 菜单列表
	 */
	@Transient
	private List<String> menus;

	/**
	 * 用户token
	 */
	@Transient
	private String token;

	/**
	 * 创建者ID
	 */
	@Column(name="create_user_id")
	private Long createUserId;

	/**
	 * 所属公司ID
	 */
	@Column(name="corp_id")
	private String corpId;

	/**
	 * 创建时间
	 */
	@CreationTimestamp
//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@UpdateTimestamp
//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

}
