package org.spring.boot.thrift.server.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;


/**
 * Created by Howell on 19/3/18.
 * 自定义健康端点 继承AbstractHealthIndicator类 也可以实现 HealthIndicator接口的
 */
@Component
public class MetricsThriftMethodInterceptor extends AbstractHealthIndicator implements MethodInterceptor {

    Health.Builder builder;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        this.builder = builder;
        this.builder.withDetail("name","thrift server").up().build();
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return methodInvocation.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            this.builder.withDetail("MethodName", methodInvocation.getMethod().getName())
                    .withDetail("ClassName", methodInvocation.getThis().getClass().getCanonicalName() + "." + methodInvocation.getMethod().getName() + "." + (endTime - startTime));
        }
    }
}