package org.spring.boot.thrift.client;

import org.apache.thrift.transport.TTransport;
import org.spring.boot.thrift.client.sleuth.ThriftTransportSpanInjector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.sleuth.SpanInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Howell on 17/1/11.
 */
@Configuration
public class ThriftClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "thriftTransportSpanInjector")
    SpanInjector<TTransport> thriftTransportSpanInjector() {
        return new ThriftTransportSpanInjector();
    }
}