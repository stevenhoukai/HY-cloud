//package com.yyicbc;
//
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//import com.yyicbc.beans.business.DTO.Ai63015Request;
//import com.yyicbc.beans.business.DTO.Ai63015Response;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.IOUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.junit.Test;
//import org.springframework.http.HttpHeaders;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.DataOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//@Slf4j
//public class generateAgreementCodeTest {
//    String link = "http://123.13.125.121:8001/dsr/dsr/ai63015.flowc?flowActionName=ai63015unxml ";
//
//
//    @Test
//    public void generate3() {
//        RestTemplate restTemplate = new RestTemplate();
//        Ai63015Request ai63015Request = new Ai63015Request();
//        try {
//            log.info("第一次远程调用开始");
//            HttpHeaders httpHeaders = new HttpHeaders();
//            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//            XmlMapper xmlMapper = new XmlMapper();
//            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
//            map.add("xml63015", xmlMapper.writeValueAsString(ai63015Request));
//            org.springframework.http.HttpEntity<MultiValueMap<String, String>> remoteRequest = new org.springframework.http.HttpEntity<MultiValueMap<String, String>>(map, httpHeaders);
//            Ai63015Response ai63015Response = restTemplate.postForObject(
//                    "http://123.13.125.121:8001/dsr/dsr/ai63015.flowc?flowActionName=ai63015unxml", remoteRequest, Ai63015Response.class);
//            log.info("第一次远程调用结果 = " + ai63015Response);
//        } catch (Exception e) {
//            log.error("调用ai63015接口异常 exception=" + e);
//        }
//    }
//    @Test
//    public void generate1() {
//        byte[] xmlData = getXmlInfo().getBytes();
//        try {
//            URL url = new URL(link);
//            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
//            urlCon.setDoOutput(true);
//            urlCon.setDoInput(true);
//            urlCon.setUseCaches(false);
//            urlCon.setRequestMethod("POST");
//            urlCon.setRequestProperty("content-Type", "application/json");
//            urlCon.setRequestProperty("charset", "utf-8");
//            urlCon.setRequestProperty("Content-length", String.valueOf(xmlData.length));
//            DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
//            printout.write(xmlData);
//            printout.flush();
//            printout.close();
//            InputStream instr = urlCon.getInputStream();
//            byte[] bis = IOUtils.toByteArray(instr);
//            String ResponseString = new String(bis, "UTF-8");
//            if ((ResponseString == null) || ("".equals(ResponseString.trim()))) {
//                System.out.println("返回空");
//            }
//            System.out.println("返回数据为:" + ResponseString);
//            if (instr != null) {
//                instr.close();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void generate2() {
//        String xmlData = getXmlInfo();
//        try {
//            CloseableHttpClient client = null;
//            CloseableHttpResponse response = null;
//            HttpPost httpPost = new HttpPost(link);
//            httpPost.setEntity(new StringEntity(xmlData, "UTF-8"));
//            client = HttpClients.createDefault();
//            response = client.execute(httpPost);
//            HttpEntity entity = response.getEntity();
//            String result = EntityUtils.toString(entity);
//            System.out.println(result);
//            if (response != null) {
//                response.close();
//            }
//            if (client != null) {
//                client.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String getXmlInfo() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<?xml version=\"1.0\"?>");
//        sb.append("<REQ>");
//        sb.append("<CHNLTYPE>1</CHNLTYPE>");
//        sb.append("<CHNLNO>011900900000059</CHNLNO>");
//        sb.append("<PROTFLG>1</PROTFLG>");
//        sb.append("<MAINPTSN>1</MAINPTSN>");
//        sb.append("<PRODSENO>2</PRODSENO>");
//        sb.append("<TELLERNO>59</TELLERNO>");
//        sb.append("<SERVFACE>1</SERVFACE>>");
//        sb.append("<OPERFLAG>4</OPERFLAG>");
//        sb.append("<AGTNAME>test</AGTNAME>");
//        sb.append("<AGTSNAME>test</AGTSNAME>");
//        sb.append("<MEDTYPE>20101</MEDTYPE>");
//        sb.append("<PRODTYPE>20101</PRODTYPE>");
//        sb.append("<ACTVDATE>1</ACTVDATE>");
//        sb.append("<FEEAMT>100</FEEAMT>");
//        sb.append("<CUSTNO>011900020000014</CUSTNO>");
//        sb.append("<SIGNCURR>81</SIGNCURR>");
//        sb.append("<SIGNCAEF>2</SIGNCAEF>");
//        sb.append("<MEDIUMID>0108000100000133649</MEDIUMID>");
//        sb.append("<PROTSENO>011910022010100000000761</PROTSENO>");
//        sb.append("<FEEMETHD>2</FEEMETHD>");
//        sb.append("<SCALEPC>0</SCALEPC>");
//        sb.append("<LOCRESF>2</LOCRESF>");
//        sb.append("<INPROTF>0</INPROTF>");
//        sb.append("</REQ>");
//        return sb.toString();
//    }
//}
