package com.yyicbc.beans.imports.PO;


import com.yyicbc.beans.business.PO.TableBasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "fova_update_data", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FovaUpdateDataPO extends TableBasePO {

    private static final long serialVersionUID = -2692500291321660285L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_import_id", columnDefinition = "varchar(30) COMMENT 'userImportId'")
    private String userImportId;

    //add by fmm 记录来源数据子表，Fova回传要更新对应子表的状态，
    @Column(name = "src_bid", columnDefinition = "varchar(30) COMMENT '来源数据子表'")
    private String srcBid;

    //add by fmm 记录来源数据子表，Fova回传要更新对应子表的状态，增加类型，反写时候根据类型判断，不用每次都去查数据库
    @Column(name = "src_type", columnDefinition = "varchar(30) COMMENT '来源類型'")
    private String srcType;

    //固定为001
    @Column(name = "fova_update_filef", columnDefinition = "varchar(3) default '001' COMMENT '文件来源标志'")
    private String fileF = "001";

    @Column(name = "fova_update_depzone", columnDefinition = "varchar(5) COMMENT '代理地区号'")
    private String depZone = "00119";

    //该网点必须在网点参数表存在并且由对应的核算网点
    @Column(name = "fova_update_depbrno", columnDefinition = "varchar(5) COMMENT '代理网点号'")
    private String depBrno = "";

    //格式为:YYYYMMDD,必须为数字
    @Column(name = "fova_update_senddate", columnDefinition = "varchar(8) COMMENT '传送日期'")
    private String sendDate;

    //每一个批号对应一组数据
    @Column(name = "fova_update_seqno", columnDefinition = "varchar(3) COMMENT '批号'")
    private String seqNo;

    @Column(name = "fova_update_status", columnDefinition = "varchar(3) default '000' COMMENT '处理标志'")
    private String status = "000";

    @Column(name = "fova_update_status_dsf", columnDefinition = "varchar(3) default '000' COMMENT 'DSF处理标志'")
    private String statusDsf = "000";

    //1：汇总，2：明细，每个客户的报盘文件中,只有一条记录是汇总记录,其他都是明细记录
    @Column(name = "fova_update_recordf", columnDefinition = "varchar(1) COMMENT '记录标志'")
    private String recordF = "2";

    //用24位;预留34位(后10位赋空格),即客户签协议时产生的协议号.对于明细记录,如果借贷标志为贷,则赋汇总记录的代理业务协议号,如果为借,则赋个人协议的协议号
    @Column(name = "fova_update_agenpsn", columnDefinition = "varchar(34) COMMENT '代理业务协议号'")
    private String agenpsn;

    //每组数据中所有记录的代理业务编号必须相同,且不足9位的前补字符0(零)
    @Column(name = "fova_update_agentno", columnDefinition = "varchar(9) COMMENT '代理业务编号'")
    private String agentNo;

    @Column(name = "fova_update_agenttp", columnDefinition = "varchar(3) COMMENT '代理业务种类'")
    private String agentTp;

    //不足34位后补空格
    @Column(name = "fova_update_mediumid", columnDefinition = "varchar(34) COMMENT '介质识别号'")
    private String mediumId;

    @Column(name = "fova_update_mediukind", columnDefinition = "varchar(3) COMMENT '介质识别种类'")
    private String mediukind;

    //用于合成协议序号
    @Column(name = "fova_update_currtype", columnDefinition = "varchar(50) COMMENT '帐户币种'")
    private String currType;

    //1-钞户；2-汇户
    @Column(name = "fova_update_cashexf", columnDefinition = "varchar(1) COMMENT '钞汇标志'")
    private String cashExf = "2";

    //必须为数值
    @Column(name = "fova_update_sernum", columnDefinition = "varchar(6) COMMENT '处理顺序号'")
    private String serNum = "1";

    //不带小数点，对汇总记录为汇总金额,最少单位是分,如金额是一块钱,那金额字段应该赋100（无处理金额时要填写0）
    @Column(name = "fova_update_amount", columnDefinition = "varchar(50) COMMENT '应处理金额'")
    private String amount;

    //格式化后 保留两位小数，单位是元
    @Column(name = "fova_update_amount2", columnDefinition = "varchar(50) COMMENT '详细金额2'")
    private String amount2;

    @Column(name = "fova_return_bustamt", columnDefinition = "varchar(17) COMMENT '实处理金额'")
    private String bustAmt;

    @Column(name = "fova_return_feeamt", columnDefinition = "varchar(17) COMMENT '实收费金额'")
    private String feeAmt;

    //一期不支持币种兑换,需与帐户币种一致
    @Column(name = "fova_update_tracurr", columnDefinition = "varchar(3) COMMENT '交易币种'")
    private String traCurr;

    //对汇总记录为汇总笔数明细记录是1,汇总记录是明细记录总数
    @Column(name = "fova_update_counter", columnDefinition = "varchar(9) COMMENT '应处理笔数'")
    private String counter = "1";

    @Column(name = "fova_return_busino", columnDefinition = "varchar(9) COMMENT '实处理笔数'")
    private String busIno;

    //1：借，2：贷
    @Column(name = "fova_update_drcrf", columnDefinition = "varchar(1) COMMENT '借贷标志'")
    private String drCrf;

    //YYYYMMDD，大于等于当前系统日期
    @Column(name = "fova_update_trandate", columnDefinition = "varchar(8) COMMENT '入帐日期'")
    private String tranDate;

    //每个客户的报盘文件中,所有记录的起息日必须一致
    //YYYYMMDD，大于系统日期为顺起息，小于系统日期为倒起息
    @Column(name = "fova_update_valuedat", columnDefinition = "varchar(8) COMMENT '起息日'")
    private String valueDat;

    //1-本行，2-他行
    //汇总方只能为1-本行
    @Column(name = "fova_update_bankflag", columnDefinition = "varchar(1) COMMENT '本他行标志'")
    private String bankFlag = "1";

    //BANKFLAG（本他行标志）为2-他行时必输
    @Column(name = "fova_update_bankcode", columnDefinition = "varchar(3) COMMENT '银行代码'")
    private String bankCode = "";

    //BANKFLAG（本他行标志）为2-他行时必输，ASCII
    @Column(name = "fova_update_shotname", columnDefinition = "varchar(20) COMMENT '客户简称'")
    private String shotName = "";

    //1-旧账号，2-新帐号
    //如果是投产以前的账号(未更新新账号)则送1，如果是投产以后新开立的账号则送2
    @Column(name = "fova_update_actflag", columnDefinition = "varchar(1) COMMENT '新旧账号标志'")
    private String actFlag = "2";

    @Column(name = "fova_update_summary", columnDefinition = "varchar(70) COMMENT '摘要'")
    private String summary = "";

    @Column(name = "fova_update_notes", columnDefinition = "varchar(12) COMMENT '备用'")
    private String notes = "";

    @Column(name = "unused", columnDefinition = "text COMMENT '未用数据'")
    private String unused;

    @Column(name = "fova_return_notes1", columnDefinition = "varchar(12) COMMENT '备用'")
    private String notes1;

    @Column(name = "fova_return_notes2", columnDefinition = "varchar(100) COMMENT '备用'")
    private String notes2;

    @Column(name = "fova_return_field2", columnDefinition = "varchar(17) COMMENT '备用'")
    private String field2;

    @Column(name = "fova_return_errno", columnDefinition = "varchar(5) COMMENT '错误码'")
    private String errNo;

    @Column(name = "fova_return_errmessage", columnDefinition = "varchar(100) COMMENT '错误信息'")
    private String errMessage;

    @Column(name = "fova_return_errmessage_dsf", columnDefinition = "varchar(100) COMMENT 'DSF错误信息'")
    private String errMessageDsf;

    //fova上传文件中的用户信息，用于对应子协议号
    @Column(name = "fova_return_customername", columnDefinition = "varchar(50) COMMENT '客户姓名'")
    private String customerName;

    //fova上传文件中的用户信息，用于对应子协议号
    @Column(name = "fova_return_customeraccount", columnDefinition = "varchar(50) COMMENT '客户账号'")
    private String customerAccount;

    //fova上传文件中的用户信息，用于对应子协议号
    @Column(name = "fova_return_customercontract", columnDefinition = "varchar(100) COMMENT '客户合同号'")
    private String customerContract;

    @Column(name = "company_code", columnDefinition = "varchar(100) COMMENT '公司代码'")
    private String companyCode;

    @Column(name = "upload_file_name", columnDefinition = "varchar(50) COMMENT '上传fova文件名'")
    private String uploadFileName;

    @Column(name = "fova_wrong_accno", columnDefinition = "varchar(2) COMMENT '错误账号'")
    private String wrongAccno;

    //导出DSF txt 用该原始账号
    @Column(name = "fova_return_customeraccount_dsf", columnDefinition = "varchar(50) COMMENT 'DSF导入原始账号'")
    private String customerAccountDsf;

}
