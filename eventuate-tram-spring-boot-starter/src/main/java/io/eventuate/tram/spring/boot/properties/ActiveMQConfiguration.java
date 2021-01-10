package io.eventuate.tram.spring.boot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableAutoConfiguration
@EnableConfigurationProperties( {
        ActiveMQConfiguration.ActiveMQProperties.class
} )
public class ActiveMQConfiguration {

    @Setter
    @Getter
    @ConfigurationProperties( "activemq" )
    static class ActiveMQProperties {
        private String url;
        private String user;
        private String password;
    }
}
