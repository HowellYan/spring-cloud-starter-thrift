package org.spring.boot.thrift.client.pool;

import brave.Span;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.thrift.TServiceClient;


/**
 * Created by Howell on 17/1/11.
 */
public class ThriftPooledObject<T extends TServiceClient> extends DefaultPooledObject<T> {

    public ThriftPooledObject(T object) {
        super(object);
    }

    public Span getSpan() {
        return span;
    }

    public void setSpan(Span span) {
        this.span = span;
    }

    private Span span;
}