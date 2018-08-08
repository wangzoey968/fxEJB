package com.it.client.util.httpClient.core;

import javafx.application.Platform;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ProgressFilterOutputStream extends FilterOutputStream {

    private ProgressCallback progressCallback;
    private long transferred;
    private long totalBytes;

    ProgressFilterOutputStream(final OutputStream out, final ProgressCallback progressCallback, final long totalBytes) {
        super(out);
        this.progressCallback = progressCallback;
        this.transferred = 0;
        this.totalBytes = totalBytes;
    }

    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
        //super.write(byte b[], int off, int len) calls write(int b)
        out.write(b, off, len);
        this.transferred += len;
        Platform.runLater(() -> {
            this.progressCallback.progress(this.transferred, this.totalBytes);
        });
    }

    @Override
    public void write(final int b) throws IOException {
        out.write(b);
        this.transferred++;
        Platform.runLater(() -> {
            this.progressCallback.progress(this.transferred, this.totalBytes);
        });

    }


}
