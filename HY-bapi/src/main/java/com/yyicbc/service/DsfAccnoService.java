package com.yyicbc.service;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * @ClassNameDsfAccnoService
 * @Description
 * @Author vic
 * @Date2020/3/11 9:46
 * @Version V1.0
 **/
public interface DsfAccnoService {


    /**功能描述: 查询财政局所有账号
     * @param
    * @return: {@link Map}
    * @Author: vic
    * @Date: 2020/3/11 9:48
    */
    public Map  findZeroMap();

    /**功能描述: 导入财局账号
     * @param paramMap
    * @return:
    * @Author: vic
    * @Date: 2020/3/17 17:53
    */
    public void importDsfAccno(Map paramMap) throws FileNotFoundException;
}
