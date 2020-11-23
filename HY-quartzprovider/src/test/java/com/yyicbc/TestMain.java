//package com.yyicbc;
//
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//public class TestMain {
//
//
//    @Test
//    public void test1(){
//        Object result = null;
////        String json = "headers={uap_usercode=test2, uap_dataSource=design, uap_token=00000171abc69dc478369bd321b95db2a3b39e3a639329cfa208fbbfc782700f5817c54a6be24ff6616f46e8db6791900407691d0d2864c429773efcbd128cf92cf61e1df21c4806d6e82dbc59009de660e7dd82e8aecabe6d017f1fed312bf85a961a5000000171abc69dc4} sendParamMap={sendData=[SendHCMUpdateVO(compensationId=1253586189554946048, pk_payroll=1001A110000000000C78, batchStatus=1, pk_wa_datas=[1001A110000000000C73, 1001A110000000000C74, 1001A110000000000C75], compensationBs=[SendHCMCompensationBVO(id=1253586190616104960, compensationId=1253586189554946048, personnelCode=C0001, accountName=熊韞端, accountNo=0119100200005935846, currencyCode=MOP, amountResult=3345762.6766666, succeedStatus=null, note=null, errorMessage=null, pk_wa_data=1001A110000000000C73, reserveField1=null, reserveField2=null, reserveField3=null, reserveField4=null, reserveField5=null), SendHCMCompensationBVO(id=1253586190616104961, compensationId=1253586189554946048, personnelCode=C0002, accountName=郭惠敏, accountNo=0119100200000138834, currencyCode=MOP, amountResult=27720.3966666, succeedStatus=null, note=null, errorMessage=null, pk_wa_data=1001A110000000000C74, reserveField1=null, reserveField2=null, reserveField3=null, reserveField4=null, reserveField5=null), SendHCMCompensationBVO(id=1253586190616104962, compensationId=1253586189554946048, personnelCode=C0003, accountName=張勇, accountNo=0108000100000373124, currencyCode=MOP, amountResult=3364747.0433333, succeedStatus=null, note=null, errorMessage=null, pk_wa_data=1001A110000000000C75, reserveField1=null, reserveField2=null, reserveField3=null, reserveField4=null, reserveField5=null)])]}";
//        String json = "headers={uap_usercode=test2, uap_dataSource=design, uap_token=00000171abc69dc478369bd321b95db2a3b39e3a639329cfa208fbbfc782700f5817c54a6be24ff6616f46e8db6791900407691d0d2864c429773efcbd128cf92cf61e1df21c4806d6e82dbc59009de660e7dd82e8aecabe6d017f1fed312bf85a961a5000000171abc69dc4} sendParamMap={sendData=[SendHCMUpdateVO(compensationId=1253586189554946048, pk_payroll=1001A110000000000C78, batchStatus=1, pk_wa_datas=[1001A110000000000C73, 1001A110000000000C74, 1001A110000000000C75], compensationBs=[SendHCMCompensationBVO(id=1253586190616104960, compensationId=1253586189554946048, personnelCode=C0001, accountName=xxx, accountNo=0119100200005935846, currencyCode=MOP, amountResult=3345762.6766666, succeedStatus=null, note=null, errorMessage=null, pk_wa_data=1001A110000000000C73, reserveField1=null, reserveField2=null, reserveField3=null, reserveField4=null, reserveField5=null), SendHCMCompensationBVO(id=1253586190616104961, compensationId=1253586189554946048, personnelCode=C0002, accountName=xxxx, accountNo=0119100200000138834, currencyCode=MOP, amountResult=27720.3966666, succeedStatus=null, note=null, errorMessage=null, pk_wa_data=1001A110000000000C74, reserveField1=null, reserveField2=null, reserveField3=null, reserveField4=null, reserveField5=null), SendHCMCompensationBVO(id=1253586190616104962, compensationId=1253586189554946048, personnelCode=C0003, accountName=xxxxx, accountNo=0108000100000373124, currencyCode=MOP, amountResult=3364747.0433333, succeedStatus=null, note=null, errorMessage=null, pk_wa_data=1001A110000000000C75, reserveField1=null, reserveField2=null, reserveField3=null, reserveField4=null, reserveField5=null)])]}";
//        try {
//            result =  JSON.parseObject(json, JSONObject.class);
//        } catch (Exception e) {
//          System.out.println(e.getMessage());
//        }
//        System.out.println(result);
//    }
//}
