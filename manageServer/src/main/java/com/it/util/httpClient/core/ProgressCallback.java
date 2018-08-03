package com.it.util.httpClient.core;

@FunctionalInterface
public interface ProgressCallback {

    public void progress(long transferred, long totalBytes);

}
