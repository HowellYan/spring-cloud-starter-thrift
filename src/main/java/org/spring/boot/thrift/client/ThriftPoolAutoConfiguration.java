package org.spring.boot.thrift.client;

import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TTransport;
import org.spring.boot.thrift.client.pool.ThriftKey;
import org.spring.boot.thrift.client.pool.ThriftPool;
import org.spring.boot.thrift.client.pool.ThriftPooledObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.sleuth.SpanInjector;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.autoconfig.TraceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertyResolver;

import javax.annotation.Resource;

/**
 * Created by Howell on 17/1/11.
 */
@Configuration
@AutoConfigureAfter({TraceAutoConfiguration.class, ThriftClientAutoConfiguration.class})
@ConditionalOnBean(Tracer.class)
public class ThriftPoolAutoConfiguration {

    @Resource
    private TProtocolFactory protocolFactory;

    @Resource
    private LoadBalancerClient loadBalancerClient;

    @Resource
    private PropertyResolver propertyResolver;

    @Value("${thrift.client.max.poolobject:10}")
    private int maxThreads;

    @Resource
    private Tracer tracer;

    @Resource
    private SpanInjector<TTransport> thriftTransportSpanInjector;

    @Bean
    public KeyedObjectPool<ThriftKey, TServiceClient> thriftClientsPool() {
        GenericKeyedObjectPoolConfig poolConfig = new GenericKeyedObjectPoolConfig();
        poolConfig.setMaxTotal(maxThreads);
        poolConfig.setMaxIdlePerKey(maxThreads);
        poolConfig.setMaxTotalPerKey(maxThreads);
        poolConfig.setJmxEnabled(false);
        ThriftPooledObjectFactory thriftPooledObjectFactory = new ThriftPooledObjectFactory();
        thriftPooledObjectFactory.setLoadBalancerClient(loadBalancerClient);
        thriftPooledObjectFactory.setPropertyResolver(propertyResolver);
        thriftPooledObjectFactory.setProtocolFactory(protocolFactory);
        thriftPooledObjectFactory.setTracer(tracer);
        thriftPooledObjectFactory.setSpanInjector(thriftTransportSpanInjector);
        return new ThriftPool(thriftPooledObjectFactory, poolConfig);
    }
}