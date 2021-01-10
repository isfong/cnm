package io.eventuate.tram.spring.boot.annotation;

import io.eventuate.tram.spring.events.publisher.TramEventsPublisherConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target( { ElementType.TYPE } )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Import( TramEventsPublisherConfiguration.class )
public @interface EnableTramEventsPublisher {
}
