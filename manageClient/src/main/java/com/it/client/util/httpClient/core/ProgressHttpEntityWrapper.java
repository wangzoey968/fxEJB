
package com.it.client.util.httpClient.core;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;

import java.io.IOException;
import java.io.OutputStream;


public class ProgressHttpEntityWrapper extends HttpEntityWrapper {

    private ProgressCallback progressCallback;

    public ProgressHttpEntityWrapper(HttpEntity entity, ProgressCallback progressCallback) {
        super(entity);
        this.progressCallback = progressCallback;
    }

    @Override
    public void writeTo(final OutputStream out) throws IOException {
        this.wrappedEntity.writeTo(out instanceof ProgressFilterOutputStream ?
                out :
                new ProgressFilterOutputStream(out, this.progressCallback, getContentLength()));
    }

}
