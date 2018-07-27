package com.it.client.util.httpClient.core;

@FunctionalInterface
public interface ProgressCallback {
    void progress(long transferred, long totalBytes);
}
