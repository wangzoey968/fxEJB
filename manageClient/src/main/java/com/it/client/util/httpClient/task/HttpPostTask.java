package com.it.client.util.httpClient.task;

import com.it.client.util.httpClient.core.HttpClient;
import com.it.client.util.httpClient.core.ProgressCallback;
import com.it.client.util.httpClient.core.ProgressHttpEntityWrapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class HttpPostTask {

    private ScheduledFuture sf;

    /**
     * 头列表；
     */
    private Map<String, String> header = new HashMap<>();

    public HttpPostTask addHeader(String key, String value) {
        header.put(key, value);
        return this;
    }

    /**
     * 参数列表；  添加Key-Value 形式的Post数据；
     */
    private Map<String, String> stringParams = new HashMap<>();

    public HttpPostTask addStringValue(String key, String value) {
        stringParams.put(key, value);
        return this;
    }

    /**
     * Post数据，Body形式；
     */
    private String stringBody = null;

    public HttpPostTask addStringBody(String data) {
        stringBody = data;
        return this;
    }

    /**
     * 访问URL
     */
    private String url;

    public HttpPostTask setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 添加文件；
     */
    private Map<String, File> files = new HashMap<>();

    public HttpPostTask addFile(String key, File file) {
        files.put(key, file);
        return this;
    }

    /**
     * 保存结果
     */
    private String resultString = null;

    public String getResultString() {
        return resultString;
    }

    /**
     * 完成；
     */
    private Boolean isSuccess = false;
    private Runnable onSuccess;

    public HttpPostTask setOnSuccess(Runnable runnable) {
        this.onSuccess = onSuccess;
        return this;
    }

    /**
     * 开始
     */
    private Boolean isStart = false;
    private Runnable onStart;

    public HttpPostTask setOnStart(Runnable onStart) {
        this.onStart = onStart;
        return this;
    }

    /**
     * 取消
     */
    private Boolean isAbort = false;
    private Runnable onAbort;

    public HttpPostTask setOnAbort(Runnable onAbort) {
        this.onAbort = onAbort;
        return this;
    }

    /**
     * 报错
     */
    private Boolean isError = false;
    private Runnable onError;

    public HttpPostTask setOnError(Runnable onError) {
        this.onError = onError;
        return this;
    }

    /**
     * 是否同步
     * 默认不同步
     */
    private boolean sync = false;

    public HttpPostTask setSync(boolean sync) {
        this.sync = sync;
        return this;
    }

    /**
     * 执行
     */
    public HttpPostTask execute(String url, ProgressCallback progressCallback) {
        if (url != null) {
            this.setUrl(url);
            return this.execute(url, progressCallback);
        } else {
            return this;
        }
    }

    /**
     * 取消；
     */
    public void abort() {
        if (sf != null && !sf.isDone()) sf.cancel(true);
        isAbort = true;
        if (onAbort != null) onAbort.run();
    }

    /**
     * 执行Post
     */
    public HttpPostTask execute(ProgressCallback progressCallback) {
        try {
            sf = HttpClient.getSchedule().schedule(new Runnable() {
                @Override
                public void run() {
                    InputStreamReader reader = null;
                    try {
                        if (url == null) throw new Exception("URL未设置");
                        isStart = true;
                        if (onStart != null) onStart.run();
                        //HttpPost
                        HttpPost httppost = new HttpPost(url);
                        RequestConfig requestConfig = RequestConfig.custom()
                                .setConnectTimeout(5000)
                                .setConnectionRequestTimeout(1000)
                                .setSocketTimeout(5000).build();
                        httppost.setConfig(requestConfig);
                        for (Map.Entry<String, String> entry : header.entrySet()) {
                            httppost.addHeader(entry.getKey(), entry.getValue());
                        }
                        //HttpEntity
                        HttpEntity httpEntity = null;

                        if (!files.isEmpty()) {
                            MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                                    .setCharset(Charset.forName("UTF-8"))
                                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                                    .setContentType(ContentType.MULTIPART_FORM_DATA);
                            for (Map.Entry<String, String> entry : stringParams.entrySet()) {
                                builder.addPart(entry.getKey(), new StringBody(entry.getValue(), ContentType.TEXT_PLAIN.withCharset("UTF-8")));
                            }
                            for (Map.Entry<String, File> entry : files.entrySet()) {
                                builder.addPart(entry.getKey(), new FileBody(entry.getValue()));
                            }
                            httpEntity = builder.build();
                        } else if (stringBody != null) {
                            httpEntity = new StringEntity(stringBody, "UTF-8");
                        } else {
                            List<NameValuePair> pas = new ArrayList<>();
                            for (Map.Entry<String, String> entry : stringParams.entrySet()) {
                                pas.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                            }
                            httpEntity = new UrlEncodedFormEntity(pas, Charset.forName("UTF-8"));
                        }

                        httppost.setEntity(progressCallback == null ? httpEntity : new ProgressHttpEntityWrapper(httpEntity, progressCallback));
                        CloseableHttpResponse res = HttpClient.getHttpClient().execute(httppost);

                        reader = new InputStreamReader(res.getEntity().getContent(), Charset.forName("UTF-8"));

                        char[] buffer = new char[1024];
                        int length = 0;
                        StringBuilder stringBuffer = new StringBuilder();
                        while ((length = reader.read(buffer)) != -1) {
                            stringBuffer.append(new String(buffer, 0, length));
                        }
                        resultString = stringBuffer.toString();
                        if (res.getStatusLine().getStatusCode() != 200) {
                            throw new Exception(res.getStatusLine().toString());
                        } else {
                            isSuccess = true;
                            if (onSuccess != null) onSuccess.run();
                            //if (onSuccess != null) onSuccess.call(resultString);
                        }
                    } catch (Exception e) {
                        isError = true;
                    } finally {
                        try {
                            if (reader != null) {
                                reader.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, 0, TimeUnit.SECONDS);
            if (sync) sf.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

}
