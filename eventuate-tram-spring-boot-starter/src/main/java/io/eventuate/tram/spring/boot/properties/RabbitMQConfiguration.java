package io.eventuate.tram.spring.boot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableAutoConfiguration
@EnableConfigurationProperties( {
        RabbitMQConfiguration.RabbitMQProperties.class,
        RabbitMQConfiguration.RabbitMQProperties.BrokerProperties.class
} )
public class RabbitMQConfiguration {

    @ConfigurationProperties( "rabbitmq" )
    static class RabbitMQProperties {
        @ConfigurationProperties( "rabbitmq.broker" )
        static class BrokerProperties {
            @Setter
            @Getter
            private String addresses;
        }

        @Setter
        @Getter
        private String url;
    }
}
