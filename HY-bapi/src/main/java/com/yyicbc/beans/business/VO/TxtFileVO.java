package com.yyicbc.beans.business.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxtFileVO {
    private List<TxtHeaderVO> title = new ArrayList<>();
    private List<TxtHeaderVO> column = new ArrayList<>();
    private List<TxtHeaderVO> footer = new ArrayList<>();
}
