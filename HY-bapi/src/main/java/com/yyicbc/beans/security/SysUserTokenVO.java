package com.yyicbc.beans.security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户Token
 * @author ste
 * 如果考虑不用redis保存会话状态
 * 该表中的数据没有过期就是在线用户;
 */

@Data
@Entity
@Table(name = "sys_user_token")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysUserTokenVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//用户id
	@Column(name="user_id")
	private Long userId;
	//token
	private String token;
	//过期时间
	private Date expireTime;
	//更新时间
	private Date updateTime;

}
