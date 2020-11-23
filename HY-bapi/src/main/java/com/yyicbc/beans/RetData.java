package com.yyicbc.beans;


import com.yyicbc.beans.enums.StatusEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 通用前台返回值封装类型
 * ste
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class RetData {

    private Integer code ;

    private String strCode ;

    private String msg;

    private RetResult result ;

    public static RetData build(Integer code, String msg){
        RetData retData = new RetData();
        retData.setCode(code);
        retData.setMsg(msg);
        return retData;
    }

    public static RetData build(String strCode, String msg){
        RetData retData = new RetData();
        retData.setStrCode(strCode);
        retData.setMsg(msg);
        return retData;
    }

    public RetData(){
    }


    public  RetData(StatusEnums input){
        this.code =input.getStatusCode();
        this.msg = input.getMsg();
    }

    public  RetData(StatusEnums input,RetResult data){
        this.code =input.getStatusCode();
        this.msg = input.getMsg();
        this.result = data;
    }


    /**功能描述:成功
    * @return: {@link RetData}
    * @Author: vic
    * @Date: 2020/3/26 10:55
    */
    public static RetData resSccess(){
        RetData retData = new RetData();
        retData.setCode(StatusEnums.SUCCESS.getStatusCode());
        retData.setMsg(StatusEnums.SUCCESS.getMsg());
        return retData;
    }

    /**功能描述:成功
     * @return: {@link RetData}
     * @Author: vic
     * @Date: 2020/3/26 10:55
     */
    public static RetData resSccess(RetResult data){
        RetData retData = new RetData();
        retData.setCode(StatusEnums.SUCCESS.getStatusCode());
        retData.setMsg(StatusEnums.SUCCESS.getMsg());
        retData.setResult(data);
        return retData;
    }

    /**功能描述:失败
     * @param error
    * @return: {@link RetData}
    * @Author: vic
    * @Date: 2020/3/26 10:54
    */
    public static RetData resFail(String error){
        RetData retData = new RetData();
        retData.setCode(StatusEnums.ERROR.getStatusCode());
        retData.setMsg(error);
        return retData;
    }
}
