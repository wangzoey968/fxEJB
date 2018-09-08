package com.it.client.util.httpClient.task;

import com.it.client.util.httpClient.core.HttpClient;
import com.it.client.util.httpClient.core.ProgressCallback;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class HttpGetTask {

    private ScheduledFuture sf = null;

    /**
     * 头列表；
     */
    private Map<String, String> header = new HashMap<>();

    public HttpGetTask addHeader(String key, String value) {
        header.put(key, value);
        return this;
    }

    /**
     * 参数列表；
     */
    private Map<String, String> stringParams = new HashMap<>();

    public HttpGetTask addStringValue(String key, String value) {
        stringParams.put(key, value);
        return this;
    }

    /**
     * 访问URL
     */
    private String url;

    public HttpGetTask setUrl(String url) {
        this.url = url;
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

    public HttpGetTask setOnSuccess(Runnable onSuccess) {
        this.onSuccess = onSuccess;
        return this;
    }

    /**
     * 开始
     */
    private Boolean isStart = false;
    private Runnable onStart;

    public HttpGetTask setOnStart(Runnable onStart) {
        this.onStart = onStart;
        return this;
    }

    /**
     * 取消
     */
    private Boolean isAbort = false;
    private Runnable onAbort;

    public HttpGetTask setOnAbort(Runnable onAbort) {
        this.onAbort = onAbort;
        return this;
    }

    /**
     * 报错
     */
    private Boolean isError = false;
    private Runnable onError;

    public HttpGetTask setOnError(Runnable onError) {
        this.onError = onError;
        return this;
    }

    /**
     * 是否同步
     * 默认不同步
     */
    private boolean sync = false;

    public HttpGetTask setSync(boolean sync) {
        this.sync = sync;
        return this;
    }

    /**
     * 执行
     */
    public HttpGetTask execute(String url) {
        this.setUrl(url);
        return this;
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
     * Get请求数据；
     */
    public HttpGetTask execute() {
        try {
            sf = HttpClient.getSchedule().schedule(new Runnable() {
                @Override
                public void run() {
                    InputStreamReader reader = null;
                    try {
                        if (url == null) throw new Exception("URL未设置");
                        isStart = true;
                        if (onStart != null) onStart.run();
                        //拼接参数；
                        StringBuffer urb = new StringBuffer(url + "?");
                        for (Map.Entry<String, String> entry : stringParams.entrySet()) {
                            urb.append(entry.getKey() + "=" + entry.getValue() + "&");
                        }
                        //HttpGet
                        HttpGet httpGet = new HttpGet(urb.toString());
                        for (Map.Entry<String, String> entry : header.entrySet()) {
                            httpGet.addHeader(entry.getKey(), entry.getValue());
                        }
                        RequestConfig requestConfig = RequestConfig.custom()
                                .setConnectionRequestTimeout(1000)
                                .setConnectTimeout(5000)
                                .setSocketTimeout(5000).build();
                        httpGet.setConfig(requestConfig);
                        //执行；
                        CloseableHttpResponse res = HttpClient.getHttpClient().execute(httpGet);
                        reader = new InputStreamReader(res.getEntity().getContent(), Charset.forName("UTF-8"));

                        char[] buff = new char[1024];
                        int length = 0;
                        StringBuffer buffer = new StringBuffer();
                        while ((length = reader.read(buff)) != -1) {
                            buffer.append(new String(buff, 0, length));
                        }
                        resultString = buffer.toString();

                        if (res.getStatusLine().getStatusCode() != 200) {
                            throw new Exception(res.getStatusLine().toString());
                        } else {
                            isSuccess = true;
                            if (onSuccess != null) onSuccess.run();
                            //if (onSuccess != null) onSuccess.call(stringBuffer.toString());
                        }
                    } catch (Exception e) {
                        isError = true;
                        if (onError != null) onError.run();
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
            }, 0, TimeUnit.MILLISECONDS);
            if (sync) {
                sf.get(10, TimeUnit.SECONDS);
                if (!sf.isDone()) sf.cancel(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }


    private String realUrl = null;

    /**
     * 下载文件；
     */
    public HttpGetTask execute(String path, String fileName, ProgressCallback progressCallBack) {
        try {
            sf = HttpClient.getSchedule().schedule(new Runnable() {
                @Override
                public void run() {
                    InputStream ins = null;
                    FileOutputStream out = null;
                    try {
                        if (url == null) throw new Exception("URL未设置");
                        isStart = true;
                        if (onStart != null) onStart.run();
                        //拼接参数；
                        StringBuffer urb = new StringBuffer(url + "?");
                        for (Map.Entry<String, String> entry : stringParams.entrySet()) {
                            urb.append(entry.getKey() + "=" + entry.getValue() + "&");
                        }
                        //探测下载地址；
                        realUrl = new URI(urb.toString()).toString();
                        HttpResponse res = getHttpResponse(realUrl);
                        if (res.getStatusLine().getStatusCode() != 200) {
                            throw new Exception(res.getStatusLine().toString());
                        }
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
                        byte[] buffer = new byte[1024];
                        int length = 0;
                        while ((length = ins.read(buffer)) != -1) {
                            out.write(buffer, 0, length);
                            processed += length;
                            if (progressCallBack != null) progressCallBack.progress(processed, totalSize);
                        }
                        Files.move(Paths.get(path + "\\" + fName + ".tmp"), Paths.get(path + "\\" + fName));
                        if (onSuccess != null) {
                            onSuccess.run();
                            //onSuccess.call(Paths.get(path + "\\" + fName).toAbsolutePath().toString());
                        }
                    } catch (Exception e) {
                        isError = true;
                        if (onError != null) onError.run();
                    } finally {
                        try {
                            if (ins != null) {
                                ins.close();
                            }
                            if (out != null) {
                                out.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, 0, TimeUnit.MILLISECONDS);
            if (sync) sf.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }


    /**
     * 用于下载文件时探测真实URL
     */
    private HttpResponse getHttpResponse(String url) throws Exception {
        HttpResponse res = null;
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
        httpGet.setConfig(requestConfig);
        for (Map.Entry<String, String> entry : header.entrySet()) {
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }
        res = HttpClient.getHttpClient().execute(httpGet);
        if (res.containsHeader("Location")) {
            realUrl = res.getFirstHeader("Location").getValue();
            return getHttpResponse(realUrl);
        }
        return res;
    }

}
