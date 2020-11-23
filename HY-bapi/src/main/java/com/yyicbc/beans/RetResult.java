package com.yyicbc.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 前台返回值封装类型
 * 包含分页信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RetResult {

    private Integer page ;

    private Integer page_size ;

    private Integer total_count;

    private Integer page_count;

    private Object item_list;
}
