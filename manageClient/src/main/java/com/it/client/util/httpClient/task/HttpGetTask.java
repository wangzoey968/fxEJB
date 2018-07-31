package com.it.client.util.httpClient.task;

import com.it.client.util.httpClient.core.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class HttpGetTask {

    /*private ScheduledFuture sf
    *//**
     * 头列表；
     *//*
    private Map<String, String> header = new HashMap<>();

    HttpGetTask addHeader(String key, String value) {
        header.put(key, value)
        return this;
    }
    *//**
     * 参数列表；
     *//*
    private Map<String, String> stringParams = new HashMap<>();

    HttpGetTask addStringValue(String key, String value) {
        stringParams.put(key, value);
        return this;
    }
    *//**
     * 访问URL
     *//*
    private String url;

    HttpGetTask setUrl(String url) {
        this.url = url;
        return this;
    }

    *//**
     * 保存结果
     *//*
    private String resultString = null;

    String getResultString() {
        return resultString
    }
    *//**
     * 完成；
     *//*
    Boolean isSuccess = false;
    Closure onSuccess = { String str -> }

    HttpGetTask setOnSuccess(Closure onSuccess) {
        this.onSuccess = onSuccess;
        return this;
    }
    *//**
     * 开始
     *//*
    Boolean isStart = false;
    Closure onStart

    HttpGetTask setOnStart(Closure onStart) {
        this.onStart = onStart;
        return this;
    }
    *//**
     * 取消
     *//*
    Boolean isAbort = false;
    Closure onAbort

    HttpGetTask setOnAbort(Closure onAbort) {
        this.onAbort = onAbort;
        return this;
    }
    *//**
     * 报错
     *//*
    Boolean isError = false;
    Closure onError = { Exception e -> e.printStackTrace() }

    HttpGetTask setOnError(Closure onError) {
        this.onError = onError;
        return this;
    }
    *//**
     * 是否同步
     *//*
    private boolean sync = false;

    HttpGetTask setSync(boolean sync) {
        this.sync = sync;
        return this;
    }

    *//**
     * 执行
     *//*
    HttpGetTask execute(String url) {
        this.setUrl(url);
        return this.execute();
    }
    *//**
     * 取消；
     *//*
    void abort() {
        if (sf != null && !sf.isDone()) sf.cancel(true)
        isAbort = true;
        if (onAbort != null) onAbort.run();
    }

    *//**
     * Get请求数据；
     *//*
    HttpGetTask execute() {
        InputStreamReader reader = null;
        try {
            sf = HttpClient.ses.schedule({
                try {
                    if (url == null) throw new Exception("URL未设置");
                    isStart = true;
                    if (onStart != null) onStart.call();
                    //拼接参数；
                    StringBuffer urb = new StringBuffer(url + "?")
                    stringParams.each { urb.append("${it.key}=${it.value}&") }
                    //HttpGet
                    HttpGet httpGet = new HttpGet(urb.toURI());
                    header.each { httpGet.addHeader(it.key, it.value) }
                    RequestConfig requestConfig = RequestConfig.custom()
                            .setConnectionRequestTimeout(1000)
                            .setConnectTimeout(5000)
                            .setSocketTimeout(5000).build();
                    httpGet.setConfig(requestConfig);
                    //执行；
                    CloseableHttpResponse res = HttpClient.httpClient.execute(httpGet);
                    reader = new InputStreamReader(res.getEntity().getContent(), Charset.forName("UTF-8"));

                    char[] buff = new char[1024];
                    int length = 0;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((length = reader.read(buff)) != -1) {
                        stringBuffer.append(new String(buff, 0, length));
                    }
                    reader.close(); reader = null;
                    resultString = stringBuffer.toString();

                    if (res.getStatusLine().getStatusCode() != 200) {
                        throw new Exception(res.getStatusLine().toString())
                    } else {
                        isSuccess = true;
                        if (onSuccess != null) onSuccess.call(stringBuffer.toString());
                    }
                } catch (e) {
                    isError = true;
                    if (onError != null) onError.call(e);
                }
            }, 0, TimeUnit.MILLISECONDS)
            if (sync) {
                sf.get(10, TimeUnit.SECONDS);
                if (!sf.isDone()) sf.cancel(true);
            }
        } catch (e) {
            e.printStackTrace()
        } finally {
            if (reader) reader.close();
        }
        return this;
    }

    *//**
     * 下载文件；
     *//*
    HttpGetTask execute(String path, String fileName, ProgressCallback progressCallBack = null) {
        InputStream ins = null;
        FileOutputStream out = null
        try {
            sf = HttpClient.ses.schedule({
                try {
                    if (url == null) throw new Exception("URL未设置");
                    isStart = true;
                    if (onStart != null) onStart.call();
                    //拼接参数；
                    StringBuffer urb = new StringBuffer(url + "?");
                    stringParams.each { urb.append("${it.key}=${it.value}&") }
                    //探测下载地址；
                    realUrl = urb.toURI().toString();
                    HttpResponse res = getHttpResponse(realUrl);
                    if (res.getStatusLine().getStatusCode() != 200) throw new Exception(res.getStatusLine().toString());
                    //输入流
                    ins = res.getEntity().getContent();
                    //输出流；
                    if (!Paths.get(path).toFile().exists()) Files.createDirectories(Paths.get(path));
                    String fName = fileName == null ? realUrl.substring(realUrl.lastIndexOf('/')) : fileName;
                    File file = new File(path + "\\" + fName + ".tmp");
                    out = new FileOutputStream(file);
                    //读取数据；
                    final long totalSize = res.getEntity().getContentLength();
                    long processed = 0;
                    byte[] buff = new byte[1024];
                    int length = 0;
                    while ((length = ins.read(buff)) != -1) {
                        out.write(buff, 0, length);
                        processed += length;
                        if (progressCallBack != null) progressCallBack.progress(processed, totalSize)
                    }
                    ins.close(); ins = null;
                    out.close(); out = null;
                    Files.move(Paths.get(path + "\\" + fName + ".tmp"), Paths.get(path + "\\" + fName));
                    if (onSuccess != null) onSuccess.call(Paths.get(path + "\\" + fName).toAbsolutePath().toString());
                } catch (e) {
                    isError = true;
                    if (onError != null) onError.call(e);
                }
            }, 0, TimeUnit.MILLISECONDS)
            if (sync) sf.get();
        } catch (e) {
            e.printStackTrace()
        } finally {
            if (ins) ins.close();
            if (out) out.close();
        }
        return this;
    }

    *//**
     * 用于下载文件时探测真实URL
     *//*
    private String realUrl;
    *//**
     * 用于下载文件时探测真实URL
     *//*
    private HttpResponse getHttpResponse(String url) throws Exception {
        HttpResponse res = null;
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
        httpGet.setConfig(requestConfig);
        header.each { httpGet.addHeader(it.key, it.value) }
        res = HttpClient.httpClient.execute(httpGet);
        if (res.containsHeader("Location")) {
            realUrl = res.getFirstHeader("Location").getValue();
            return getHttpResponse(realUrl);
        }
        return res;
    }*/


}
