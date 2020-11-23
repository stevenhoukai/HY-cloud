

package com.yyicbc.beans.security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户与角色对应关系
 *
 * @author steven
 */
@Data
@Table(name = "sys_user_role")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysUserRoleVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 角色ID
	 */
	private Long roleId;

	
}
