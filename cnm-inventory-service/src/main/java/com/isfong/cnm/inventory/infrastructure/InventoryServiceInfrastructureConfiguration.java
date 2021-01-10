package com.isfong.cnm.inventory.infrastructure;

import io.eventuate.tram.spring.boot.annotation.EnableTramJdbcKafka;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTramJdbcKafka
@EnableTransactionManagement
public class InventoryServiceInfrastructureConfiguration {
}
