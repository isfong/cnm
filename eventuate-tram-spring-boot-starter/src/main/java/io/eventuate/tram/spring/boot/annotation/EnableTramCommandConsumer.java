package io.eventuate.tram.spring.boot.annotation;

import io.eventuate.tram.spring.commands.consumer.TramCommandConsumerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target( { ElementType.TYPE } )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Import( TramCommandConsumerConfiguration.class )
public @interface EnableTramCommandConsumer {
}
