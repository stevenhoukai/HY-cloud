package com.yyicbc.service;

import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.business.PO.FovaReturnTempPO;

import java.util.List;
import java.util.Map;

/**
 *@author vic fu
 */
public interface FovaReturnTempDataService {


    /**功能描述:根据中间表更新fovadata数据，
     * 并且更新发放薪资子表数据
     * @param tempList
    * @return:
    * @Author: vic
    * @Date: 2020/4/24 17:44
    */
    void updateFovaDataByTemp(List<FovaReturnTempPO> tempList) throws BusinessException;


    /**功能描述:保存接口数据
     * @param paramMap
    * @return:
    * @Author: vic
    * @Date: 2020/4/24 17:42
    */
    List<FovaReturnTempPO> saveTempData(Map paramMap)throws BusinessException;
}
