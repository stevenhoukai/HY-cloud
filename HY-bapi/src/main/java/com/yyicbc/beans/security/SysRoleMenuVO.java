

package com.yyicbc.beans.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色与菜单对应关系
 *
 * @author steven
 */
@Data
@Table(name = "sys_role_menu")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysRoleMenuVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 菜单ID
	 */
	private Long menuId;


	/**
	 * 菜单编码
	 */
	private String menuCode;
	
}
