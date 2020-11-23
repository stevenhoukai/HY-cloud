package com.yyicbc.beans.imports.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FovaReturnDataVO implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    //固定为001
    private String FileF;
    private String DepZone;
    //该网点必须在网点参数表存在并且由对应的核算网点
    private String DepBrno;
    //格式为:YYYYMMDD,必须为数字
    private String SenDate;
    //每一个批号对应一组数据
    private String SeqNo;
    private String Status;
    //1：汇总，2：明细，每个客户的报盘文件中,只有一条记录是汇总记录,其他都是明细记录
    private String RecordF;
    //用24位;预留34位(后10位赋空格),即客户签协议时产生的协议号.对于明细记录,如果借贷标志为贷,则赋汇总记录的代理业务协议号,如果为借,则赋个人协议的协议号
    private String Agenpsn;
    //每组数据中所有记录的代理业务编号必须相同,且不足9位的前补字符0(零)
    private String AgentNo;
    private String AgentTp;
    //不足34位后补空格
    private String MediumId;
    private String Mediukind;
    //用于合成协议序号
    private String CurrType;
    //1-钞户；2-汇户
    private String CashExf;
    //必须为数值
    private String SerNum;
    //不带小数点，对汇总记录为汇总金额,最少单位是分,如金额是一块钱,那金额字段应该赋100（无处理金额时要填写0）
    private String Amount;
    private String BustAmt;
    private String FeeAmt;
    //一期不支持币种兑换,需与帐户币种一致
    private String TraCurr;
    //对汇总记录为汇总笔数明细记录是1,汇总记录是明细记录总数
    private String Counter;
    private String BusIno;
    //1：借，2：贷
    private String DrCrf;
    //YYYYMMDD，大于等于当前系统日期
    private String TranDate;
    //每个客户的报盘文件中,所有记录的起息日必须一致
    //YYYYMMDD，大于系统日期为顺起息，小于系统日期为倒起息
    private String ValueDat;
    //1-本行，2-他行
    //汇总方只能为1-本行
    private String BankFlag;
    //BANKFLAG（本他行标志）为2-他行时必输
    private String BankCode;
    //BANKFLAG（本他行标志）为2-他行时必输，ASCII
    private String ShotName;
    //1-旧账号，2-新帐号
    //如果是投产以前的账号(未更新新账号)则送1，如果是投产以后新开立的账号则送2
    private String ActFlag;
    private String Summary;
    private String Notes1;
    private String Notes2;
    private String Field2;
    private String ErrNo;
    private String ErrMessage;
}
