package io.eventuate.tram.spring.boot.annotation;

import io.eventuate.tram.spring.jdbckafka.TramJdbcKafkaConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target( { ElementType.TYPE } )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Import( { TramJdbcKafkaConfiguration.class } )
public @interface EnableTramJdbcKafka {
}
