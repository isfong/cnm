package io.eventuate.tram.spring.boot.annotation;

import io.eventuate.tram.spring.commands.producer.TramCommandProducerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target( { ElementType.TYPE } )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Import( TramCommandProducerConfiguration.class )
public @interface EnableTramCommandProducer {
}
