package com.yyicbc.beans.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单管理（不做后台处理，前台menuconfig进行管理）
 *
 * @author steven
 */
@Data
@Table(name = "sys_menu")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysMenuVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 菜单ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long menuId;

	/**
	 * 父菜单ID，一级菜单为0
	 */
	private Long parentId;
	
	/**
	 * 父菜单名称
	 */
	@Transient
	private String parentName;

	/**
	 * 菜单名称
	 */
	private String name;

	/**
	 * 菜单URL
	 */
	private String url;

	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	private String perms;

	/**
	 * 类型     0：目录   1：菜单   2：按钮
	 */
	private Integer type;

	/**
	 * 菜单图标
	 */
	private String icon;

	/**
	 * 排序
	 */
	private Integer orderNum;
	
	/**
	 * tree属性
	 */
	@Transient
	private Boolean open;

	@Transient
	private List<?> list;

}
