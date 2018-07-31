package com.it.client.util.httpClient.task;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import java.nio.charset.Charset;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class HttpPostTask {

    /*private ScheduledFuture sf

    *//**
     * 头列表；
     *//*
    private Map<String, String> header = new HashMap<>();

    HttpPostTask addHeader(String key, String value) {
        header.put(key, value)
        return this;
    }
    *//**
     * 参数列表；  添加Key-Value 形式的Post数据；
     *//*
    private Map<String, String> stringParams = new HashMap<>();

    HttpPostTask addStringValue(String key, String value) {
        stringParams.put(key, value);
        return this;
    }
    *//**
     * Post数据，Body形式；
     *//*
    private String stringBody = null;

    HttpPostTask addStringBody(String data) {
        stringBody = data;
        return this;
    }
    *//**
     * 访问URL
     *//*
    private String url;

    HttpPostTask setUrl(String url) {
        this.url = url;
        return this;
    }
    *//**
     * 添加文件；
     *//*
    private Map<String, File> files = new HashMap<>();

    HttpPostTask addFile(String key, File file) {
        files.put(key, file);
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

    HttpPostTask setOnSuccess(Closure onSuccess) {
        this.onSuccess = onSuccess;
        return this;
    }
    *//**
     * 开始
     *//*
    Boolean isStart = false;
    Closure onStart

    HttpPostTask setOnStart(Closure onStart) {
        this.onStart = onStart;
        return this;
    }
    *//**
     * 取消
     *//*
    Boolean isAbort = false;
    Closure onAbort

    HttpPostTask setOnAbort(Closure onAbort) {
        this.onAbort = onAbort;
        return this;
    }
    *//**
     * 报错
     *//*
    Boolean isError = false;
    Closure onError = { Exception e -> e.printStackTrace() }

    HttpPostTask setOnError(Closure onError) {
        this.onError = onError;
        return this;
    }

    *//**
     * 是否同步
     *//*
    private boolean sync = false;

    HttpPostTask setSync(boolean sync) {
        this.sync = sync;
        return this;
    }

    *//**
     * 执行
     *//*
    HttpPostTask execute(String url, ProgressCallback progressCallback = null) {
        this.setUrl(url);
        return this.execute(progressCallback);
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
     * 执行Post
     *//*
    HttpPostTask execute(ProgressCallback progressCallback = null) {
        InputStreamReader reader = null;
        try {
            sf = HttpClient.ses.schedule({
                try {
                    if (url == null) throw new Exception("URL未设置")
                    this.isStart = true;
                    if (onStart != null) onStart.run();
                    //HttpPost
                    HttpPost httppost = new HttpPost(url);
                    RequestConfig requestConfig = RequestConfig.custom()
                            .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                            .setSocketTimeout(5000).build();
                    httppost.setConfig(requestConfig);
                    header.each { httppost.addHeader(it.key, it.value) }
                    //HttpEntity
                    HttpEntity httpEntity = null;

                    if (!files.isEmpty()) {
                        MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                                .setCharset(Charset.forName("UTF-8"))
                                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                                .setContentType(ContentType.MULTIPART_FORM_DATA);
                        stringParams.each { builder.addPart(it.key, new StringBody(it.value, ContentType.TEXT_PLAIN.withCharset("UTF-8"))) }
                        files.each { builder.addPart(it.key, new FileBody(it.value)) }
                        httpEntity = builder.build();
                    } else if (stringBody != null) {
                        httpEntity = new StringEntity(stringBody, 'UTF-8');
                        httpEntity.setContentEncoding("UTF-8");
                    } else {
                        List<NameValuePair> pas = new ArrayList<>();
                        stringParams.each { pas.add(new BasicNameValuePair(it.key, it.value)) }
                        httpEntity = new UrlEncodedFormEntity(pas, Charset.forName("UTF-8"));
                    }

                    httppost.setEntity(progressCallback == null ? httpEntity : new ProgressHttpEntityWrapper(httpEntity, progressCallback));
                    CloseableHttpResponse res = HttpClient.httpClient.execute(httppost);

                    reader = new InputStreamReader(res.getEntity().getContent(), Charset.forName("UTF-8"));

                    char[] buff = new char[1024];
                    int length = 0;
                    StringBuilder stringBuffer = new StringBuilder();
                    while ((length = reader.read(buff)) != -1) {
                        stringBuffer.append(new String(buff, 0, length));
                    }
                    reader.close(); reader = null;
                    resultString = stringBuffer.toString();
                    if (res.getStatusLine().getStatusCode() != 200) {
                        throw new Exception(res.getStatusLine().toString());
                    } else {
                        isSuccess = true;
                        if (onSuccess != null) onSuccess.call(resultString);
                    }
                } catch (e) {
                    isError = true;
                    if (onError != null) onError.call(e);
                }
            }, 0, TimeUnit.MILLISECONDS)
            if (sync) sf.get();
        } catch (e) {
            e.printStackTrace()
        } finally {
            if (reader) reader.close();
        }
        return this;
    }*/

}
