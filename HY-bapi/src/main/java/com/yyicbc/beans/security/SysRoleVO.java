

package com.yyicbc.beans.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色
 *
 * @author steven
 */
@Data
@Table(name = "sys_role",uniqueConstraints=@UniqueConstraint(columnNames="role_code"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysRoleVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
	private Long roleId;

	/**
	 * 角色编码
	 */
	@NotBlank(message="角色编码不能为空")
	@Column(name="role_code",columnDefinition = "varchar(50) COMMENT '角色编码'" )
	private String roleCode;

	/**
	 * 角色名称
	 */
	@NotBlank(message="角色名称不能为空")
	@Column(name="role_name",columnDefinition = "varchar(100) COMMENT '角色名称'" )
	private String roleName;

	/**
	 * 授权时间
	 */
//	@Temporal(TemporalType.TIME)
	@Column(name="ayth_time")
	private Date authTime;

	/**
	 * 授权人id
	 */
	@Column(name="auth_user_id")
	private Long authUserId;
	/**
	 * 授权人编码
	 */
	@Transient
	private String authUserCode;

	@Transient
	private List<String> menus;

	/**
	 * 状态  1：禁用   0：启用
	 */
	@Column(name="status" )
	private Integer status = 0;

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
