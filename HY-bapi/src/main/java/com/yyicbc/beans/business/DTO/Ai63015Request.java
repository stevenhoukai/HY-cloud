package com.yyicbc.beans.business.DTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "REQ")
public class Ai63015Request implements Serializable {

    private static final long serialVersionUID = 5266477548557573183L;

    @JacksonXmlProperty(localName = "PROTFLG")
    private String PROTFLG = "1";//签约方向标志 1发起方；2从属方

    @JacksonXmlProperty(localName = "MAINPTSN")
    private String MAINPTSN = "";    //代理业务主协议号: 新增时签约方向为1发起方时不输，为2从属方时必输；修改时必输,删除时必输

    @JacksonXmlProperty(localName = "PRODSENO")
    private String PRODSENO ="";//产品序号

    @JacksonXmlProperty(localName = "FEESENO")
    private String FEESENO ="";//费用序号与产品序号一致

    @JacksonXmlProperty(localName = "OPERFLAG")
    private String OPERFLAG="";//动作标识： 2. 修改 3. 刪除 4. 新增

    @JacksonXmlProperty(localName = "MEDIUMID")
    private String MEDIUMID = "";//介质识别号:解冻时协议发起，介质识别号可以不输；冻结时，介质识别号必输

    @JacksonXmlProperty(localName = "AGTSNAME")
    private String AGTSNAME="";//代理业务简称

    @JacksonXmlProperty(localName = "MEDTYPE")
    private String MEDTYPE = "";//介质种类：介质种类：30101-网银；30102-电话银行 20101


    @JacksonXmlProperty(localName = "PRODTYPE")
    private String PRODTYPE ="";//产品种类: 20101 – 代理业务产品种类

    @JacksonXmlProperty(localName = "ACTVDATE")
    private String ACTVDATE ="";//生效日期

    @JacksonXmlProperty(localName = "FEEAMT")
    private String FEEAMT = "";//费用金额

    @JacksonXmlProperty(localName = "FEEFLAG")
    private String FEEFLAG = "";//收费标志：1不收；2发起方收；3从属方收；4同时从双方收；从属方的收费标志必须和发起方协议一致

    @JacksonXmlProperty(localName = "CUSTNO")
    private String CUSTNO ="";//客户编号

    @JacksonXmlProperty(localName = "SIGNCURR")
    private String SIGNCURR ="";//币种: 1.人民幣  13.港幣  81.澳門幣

    @JacksonXmlProperty(localName = "SIGNCAEF")
    private String SIGNCAEF = "";//钞汇标志:  签订协议账号钞汇标志, 新增/修改必输，输入新账号的钞汇标志

    @JacksonXmlProperty(localName = "PROTSENO")
    private String PROTSENO="";//协议编号

    @JacksonXmlProperty(localName = "FEEMETHD")
    private String FEEMETHD ="";//收费方式:收费方式（收费标志为1时不输）: 1按金额收2按笔数收；3按所有笔数收；4按固定金额收。从属放的收费方式必须和发起方的一致

    @JacksonXmlProperty(localName = "SCALEPC")
    private String SCALEPC ="";//按金额收费的比例:  收费方式为1时输相关值，为2时输0。从属放的收费必须和发起方的一致

    @JacksonXmlProperty(localName = "INPROTF")
    private String INPROTF ="";//内部协议标志: 0 否，1是

    @JacksonXmlProperty(localName = "MEDIDTP")
    private String MEDIDTP ="";//介质类型: 0.接口自行判断 1.卡 2.帐户协议 3.内部户帐号 4.表外户 5．虚拟介质

    @JacksonXmlProperty(localName = "RESERFLG")
    private String RESERFLG ="";//计费币种（收费标志为1时不输）  人民币： 001、 港币：013、澳币：081

    @JacksonXmlProperty(localName = "CORPFLAG")
    private String CORPFLAG="";//企业签名标志: 0没有签名；1有签名

    @JacksonXmlProperty(localName = "SUMMARY")
    private String SUMMARY="";//摘要

    @JacksonXmlProperty(localName = "FIELD1")
    private String FIELD1=""; //最高金额

    @JacksonXmlProperty(localName = "FIELD2")
    private String FIELD2=""; //最低金额

    @JacksonXmlProperty(localName = "STPCUR")
    private String STPCUR=""; //止付币种

    @JacksonXmlProperty(localName = "STPAMT")
    private String STPAMT=""; //单次止付金额

    @JacksonXmlProperty(localName = "STTAMT")
    private String STTAMT=""; //每月止付金额

    @JacksonXmlProperty(localName = "CTDATE")
    private String CTDATE="";//创建日期

    @JacksonXmlProperty(localName = "DRCRF")
    private String DRCRF="";//借贷标志: 1:借2:贷

    @JacksonXmlProperty(localName = "TRNCODE")
    private String TRNCODE="";//交易代码

    @JacksonXmlProperty(localName = "LTDATE")
    private String LTDATE=""; //上次修改日期

    @JacksonXmlProperty(localName = "NOTES")
    private String NOTES=""; //备注

//    @JacksonXmlProperty(localName = "CHNLTYPE")
//    private String CHNLTYPE="9"; //渠道种类:9-手机, 10-网銀
//
//    @JacksonXmlProperty(localName = "TELLERNO")
//    private String TELLERNO ="59";//櫃員號：59-手机, 12-网銀
//
//    @JacksonXmlProperty(localName = "SERVFACE")
//    private String SERVFACE ="1";//服务界面：53-手机, 10网銀
//
//
//    @JacksonXmlProperty(localName = "AGTNAME")
//    private String AGTNAME="test";//代理业务种类名称
//
//    @JacksonXmlProperty(localName = "MEDSENO")
//    private String MEDSENO="0";//下挂协议序号
//
//    @JacksonXmlProperty(localName = "LOCRESF")
//    private String LOCRESF = "2";//居民标志:  1公司，2个人
}