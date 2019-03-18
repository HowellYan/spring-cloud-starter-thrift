package org.spring.boot.thrift.client.sleuth;

import brave.propagation.Propagation;
import brave.propagation.TraceContext;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.spring.boot.thrift.client.transport.TLoadBalancerClient;

import java.util.List;

/**
 * Created by Howell on 17/1/11.
 */
public final class ThriftTransportSpanInjector implements Propagation<TTransport> {


    @Override
    public List<TTransport> keys() {
        return null;
    }

    @Override
    public <C> TraceContext.Injector<C> injector(Setter<C, TTransport> setter) {
        return null;
    }

    @Override
    public <C> TraceContext.Extractor<C> extractor(Getter<C, TTransport> getter) {
        return null;
    }

    public interface KeyFactory<K> {
        Propagation.KeyFactory<String> STRING = new Propagation.KeyFactory<String>() {
            public String create(String name) {
                return "thriftTransportSpanInjector";
            }

            public String toString() {
                return "StringKeyFactory{}";
            }
        };
        K create(String var1);
    }

    static final class ExtraFieldInjector<C, K> implements TraceContext.Injector<C> {
        @Override
        public void inject(TraceContext traceContext, C c) {

        }
    }
}