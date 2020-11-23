package com.yyicbc.beans.business.DTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Ai63015Response implements Serializable
{
    private static final long serialVersionUID = 3606215900666628069L;

    @JacksonXmlProperty(localName = "TRANSOK")
    private String TRANSOK;//0：正常；1：出错

    @JacksonXmlProperty(localName = "ERR_NO")
    private String ERR_NO;//出错信息：当交易失败返回，TRANSOK为1时，ERR_NO中返回一个错误信息码，当交易成功返回时无效

    @JacksonXmlProperty(localName = "TABLE_NAME")
    private String TABLE_NAME;

    @JacksonXmlProperty(localName = "TSF_STAT")
    private String TSF_STAT;

    @JacksonXmlProperty(localName = "TRXSQNB")
    private String TRXSQNB;
    @JacksonXmlProperty(localName = "ZONENO")
    private String ZONENO;

    @JacksonXmlProperty(localName = "WORKDATE")
    private String WORKDATE;//银行业务日期

    @JacksonXmlProperty(localName = "LCLDATE")
    private String LCLDATE;//本地日期

    @JacksonXmlProperty(localName = "LCLTIME")
    private String LCLTIME;//本地时间

    @JacksonXmlProperty(localName = "MFDATE")
    private String MFDATE;//本地时间

    @JacksonXmlProperty(localName = "MFTIME")
    private String MFTIME;//本地时间

    @JacksonXmlProperty(localName = "DAPCODE")
    private String DAPCODE;//时区代码

    @JacksonXmlProperty(localName = "MSG_NO")
    private String MSG_NO;//信息代码序号)

    @JacksonXmlProperty(localName = "PROTSENO")
    private String PROTSENO;//协议编号

    @JacksonXmlProperty(localName = "CTCERTYP")
    private String CTCERTYP;//创建人证件类型

    @JacksonXmlProperty(localName = "CTCUSNAM")
    private String CTCUSNAM;//创建人姓名


    @JacksonXmlProperty(localName = "COPRFLAG")
    private String COPRFLAG;//公司个人标志

    @JacksonXmlProperty(localName = "LOCRESF")
    private String LOCRESF;//居民标志: 1公司，2个人

    @JacksonXmlProperty(localName = "LTCERTYP")
    private String LTCERTYP;//上次修改证件类型

    @JacksonXmlProperty(localName = "LTIDCODE")
    private String LTIDCODE;//上次修改证件号码

    @JacksonXmlProperty(localName = "LTCUSNAM")
    private String LTCUSNAM;//上次修改姓名


    @JacksonXmlProperty(localName = "COPRFLAG_1")
    private String COPRFLAG_1;


    @JacksonXmlProperty(localName = "LOCRESF_1")
    private String LOCRESF_1;

}