package com.yyicbc.beans.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public final class HttpUtil {

    private HttpUtil() {}

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 90000;

    static {
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    public static JSONObject get(String url, Map<String, String> headers, Map<String, String> params) {
        ArrayList paramList = new ArrayList();
        HttpGet httpGet = null;
        if (null != params && params.size() > 0) {
            for (String key : params.keySet()) {
                paramList.add(key + "=" + params.get(key));
            }
            String paramStr = ListUtil.join(paramList, "&");
            httpGet = new HttpGet(url + "?" + paramStr);
        } else {
            httpGet = new HttpGet(url);
        }
        if (null != headers && headers.size() > 0) {
            for (Map.Entry<String, String> stringStringEntry : headers.entrySet()) {
                httpGet.setHeader(stringStringEntry.getKey(), stringStringEntry.getValue());
            }
        }
        return parseJson(executeHttp(httpGet, url.startsWith("https://")), JSONObject.class);
    }

    public static JSONObject get(String url, Map<String, String> params) {
        return get(url, null, params);
    }

    public static JSONObject get(String url) {
        HttpGet httpGet = new HttpGet(url);
        return parseJson(executeHttp(httpGet, url.startsWith("https://")), JSONObject.class);
    }

    public static JSONObject post(String url, Object params) {
        return post(url, params, null, JSONObject.class);
    }

    public static <T extends Object> T post(String url, Object params,Map<String, String> headers, Class<T> resultClass) {
        HttpPost httpPost = new HttpPost(url);
        try {
            StringEntity entity = new StringEntity(JSON.toJSONString(params), Consts.UTF_8);
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            if (headers != null) {
                BasicHeader[] mHeaders = new BasicHeader[headers.size()];
                BasicHeader header;
                int index = 0;
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    header = new BasicHeader(entry.getKey(), entry.getValue());
                    mHeaders[index] = header;
                    index += 1;
                }
                httpPost.setHeaders(mHeaders);
            }
            String result = executeHttp(httpPost, url.startsWith("https://"));
            if (resultClass.equals(JSONObject.class)) {
                return (T) parseJson(result, JSONObject.class);
            }
            else if (resultClass.equals(JSONArray.class)) {
                return (T) parseJson(result, JSONArray.class);
            }
            else {
                return (T)result;
            }

        } catch (Exception exp) {
            logger.error("do http failed.", exp);
        }
        return null;
    }

    public static <T extends Object> T post(String url, Object params,Map<String, String> headers, Class<T> resultClass, String charset) {
        HttpPost httpPost = new HttpPost(url);
        try {
            StringEntity entity = new StringEntity(JSON.toJSONString(params), charset);
            entity.setContentEncoding(charset);
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            if (headers != null) {
                BasicHeader[] mHeaders = new BasicHeader[headers.size()];
                BasicHeader header;
                int index = 0;
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    header = new BasicHeader(entry.getKey(), entry.getValue());
                    mHeaders[index] = header;
                    index += 1;
                }
                httpPost.setHeaders(mHeaders);
            }
            String result = executeHttp(httpPost, url.startsWith("https://"));
            if (resultClass.equals(JSONObject.class)) {
                return (T) parseJson(result, JSONObject.class);
            }
            else if (resultClass.equals(JSONArray.class)) {
                return (T) parseJson(result, JSONArray.class);
            }
            else {
                return (T)result;
            }

        } catch (Exception exp) {
            logger.error("do http failed.", exp);
        }
        return null;
    }


    public static CloseableHttpClient createHttpsClient(){
        SSLConnectionSocketFactory sslConnSocketFactory = createSSLConnSocketFactory();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory> create().register("https", sslConnSocketFactory)
                .build();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(cm.getMaxTotal());
        return HttpClients.custom().setSSLSocketFactory(sslConnSocketFactory).setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
    }



    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(
                    sslContext, new X509HostnameVerifier() {
                @Override
                public void verify(String host, SSLSocket ssl)
                        throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert)
                        throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns,
                                   String[] subjectAlts) throws SSLException {
                }

                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
        } catch (GeneralSecurityException e) {
            logger.error("make ssl connection factory failed. ", e);
        }
        return sslsf;
    }


    private static String executeHttp(HttpRequestBase req, boolean ssl) {
        HttpClient httpclient;
        if (ssl) {
            httpclient = createHttpsClient();
        }
        else {
            HttpClientBuilder builder = HttpClientBuilder.create();
            httpclient = builder.build();
        }

        req.setConfig(requestConfig);

        try {
            HttpResponse response = httpclient.execute(req);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String httpStr = EntityUtils.toString(entity, "utf-8");
                entity.getContent().close();
                return httpStr;
            }
        }
        catch (SocketTimeoutException timeout) {
            logger.error("链接超时, {}",req.getURI(), timeout);
            throw new RuntimeException("链接超时, " + req.getURI());
        }
        catch (Exception e) {
            logger.error("do http request failed.", e);
            return null;
        }

        return null;
    }

    private static <T extends JSON> T parseJson(String json, Class<T> clz) {
        if (json == null) {
            return null;
        }
        try {
            return JSON.parseObject(json, clz);
        } catch (Exception e) {
            logger.error("parse json failed.", e);
        }
        return null;
    }

    /**
     * 支持的Http method
     *
     */
    private static enum HttpMethod {
        POST,DELETE,GET,PUT,HEAD;
    };

    private static String invokeUrl(String url, Map params, Map<String,String> headers, int connectTimeout, int readTimeout, String encoding, HttpMethod method){
        //构造请求参数字符串
        StringBuilder paramsStr = null;
        if(params != null){
            paramsStr = new StringBuilder();
            Set<Map.Entry> entries = params.entrySet();
            for(Map.Entry entry:entries){
                String value = (entry.getValue()!=null)?(String.valueOf(entry.getValue())):"";
                paramsStr.append(entry.getKey() + "=" + value + "&");
            }
            //只有POST方法才能通过OutputStream(即form的形式)提交参数
            if(method != HttpMethod.POST){
                url += "?"+paramsStr.toString();
            }
        }

        URL uUrl = null;
        HttpURLConnection conn = null;
        BufferedWriter out = null;
        BufferedReader in = null;
        try {
            //创建和初始化连接
            uUrl = new URL(url);
            conn = (HttpURLConnection) uUrl.openConnection();
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            conn.setRequestMethod(method.toString());
            //禁止跳转
            conn.setInstanceFollowRedirects(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置连接超时时间
            conn.setConnectTimeout(connectTimeout);
            //设置读取超时时间
            conn.setReadTimeout(readTimeout);
            //指定请求header参数
            if(headers != null && headers.size() > 0){
                Set<String> headerSet = headers.keySet();
                for(String key:headerSet){
                    conn.setRequestProperty(key, headers.get(key));
                }
            }

            if(paramsStr != null && method == HttpMethod.POST){
                //发送请求参数
                out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),encoding));
                int statusCode = conn.getResponseCode();
                System.out.println("返回状态==="+statusCode);
                out.write(paramsStr.toString());
                out.flush();
            }

            //接收返回结果
            StringBuilder result = new StringBuilder();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),encoding));
            if(in != null){
                String line = "";
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("调用接口["+url+"]失败！请求URL："+url+"，参数："+params,e);
            //处理错误流，提高http连接被重用的几率
            try {
                byte[] buf = new byte[100];
                InputStream es = conn.getErrorStream();
                if(es != null){
                    while (es.read(buf) > 0) {;}
                    es.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (out!=null) {
                    out.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (in !=null) {
                    in.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            //关闭连接
            if (conn != null){
                conn.disconnect();
            }
        }
        return null;
    }

    /**
     * GET方法提交Http请求，语义为“查询”
     * @param url 资源路径（如果url中已经包含参数，则params应该为null）
     * @param params 参数
     * @param connectTimeout 连接超时时间（单位为ms）
     * @param readTimeout 读取超时时间（单位为ms）
     * @param charset 字符集（一般该为“utf-8”）
     * @return
     */
    public static String get(String url, Map params, int connectTimeout, int readTimeout, String charset){
        return invokeUrl(url,params,null,connectTimeout,readTimeout,charset,HttpMethod.GET);
    }

    public static JSONObject put(String url, String requestBody) {
        return put(url, null, requestBody, null);
    }

    public static JSONObject put(String url, Map<String, String> urlParams, String requestBody, Map<String, String> headers) {
        HttpPut httpPut;
        if (urlParams != null && !urlParams.isEmpty()) {
            ArrayList paramList = new ArrayList();
            for (String key : urlParams.keySet()) {
                paramList.add(key + "=" + urlParams.get(key));
            }
            String paramStr = ListUtil.join(paramList, "&");
            httpPut = new HttpPut(url + "?" + paramStr);
        } else {
            httpPut = new HttpPut(url);
        }

        try {
            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody, Consts.UTF_8);
                entity.setContentType("application/json");
                entity.setContentEncoding("UTF-8");
                httpPut.setEntity(entity);
            }

            if (headers != null) {
                int index = 0;
                BasicHeader[] mHeaders = new BasicHeader[headers.size()];
                BasicHeader header;
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    header = new BasicHeader(entry.getKey(), entry.getValue());
                    mHeaders[index] = header;
                    index += 1;
                }
                httpPut.setHeaders(mHeaders);
            }
            return parseJson(executeHttp(httpPut, url.startsWith("https://")));
        } catch (Exception exp) {
            logger.error("do http put failed.", exp);
        }
        return null;
    }

    public static JSONObject delete(String url, Map<String, String> params) {
        ArrayList paramList = new ArrayList();
        for (String key : params.keySet()) {
            paramList.add(key + "=" + params.get(key));
        }
        String paramStr = ListUtil.join(paramList, "&");
        HttpDelete httpDelete = new HttpDelete(url + "?" + paramStr);
        return parseJson(executeHttp(httpDelete, url.startsWith("https://")));
    }

    private static JSONObject parseJson(String json) {
        if (json == null) {
            return null;
        }
        try {
            return JSON.parseObject(json, JSONObject.class);
        } catch (Exception e) {
            logger.error("parse json failed.", e);
        }
        return null;
    }
}
