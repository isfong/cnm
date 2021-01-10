package io.eventuate.tram.spring.boot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableAutoConfiguration
@EnableConfigurationProperties( {
        EventuateIOConfiguration.Eventuate.DatabaseProperties.class,
        EventuateIOConfiguration.Eventuate.RedisProperties.class,
        EventuateIOConfiguration.Local.KafkaProperties.class,
        EventuateIOConfiguration.Local.ZookeeperProperties.class
} )
public class EventuateIOConfiguration {
    static class Eventuate {
        @ConfigurationProperties( prefix = "eventuate.database" )
        static class DatabaseProperties {
            @Setter
            @Getter
            private String schema;
        }

        @ConfigurationProperties( prefix = "eventuate.redis" )
        static class RedisProperties {
            @Setter
            @Getter
            private String servers;
            @Setter
            @Getter
            private Integer partitions;
        }
    }

    static class Local {
        @ConfigurationProperties( "eventuatelocal.kafka.bootstrap" )
        public static class KafkaProperties {
            @Setter
            @Getter
            private String servers;
        }

        @ConfigurationProperties( "eventuatelocal.zookeeper.connection" )
        public static class ZookeeperProperties {
            @Setter
            @Getter
            private String string;
        }
    }
}
