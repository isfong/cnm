package com.isfong.cnm.product.infrastructure;

import io.eventuate.tram.spring.boot.annotation.EnableOptimisticLockingDecorator;
import io.eventuate.tram.spring.boot.annotation.EnableTramJdbcKafka;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTramJdbcKafka
@EnableTransactionManagement
@EnableOptimisticLockingDecorator
public class ProductServiceInfrastructureConfiguration {
}
