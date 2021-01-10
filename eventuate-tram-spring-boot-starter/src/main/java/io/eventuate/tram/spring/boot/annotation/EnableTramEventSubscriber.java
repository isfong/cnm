package io.eventuate.tram.spring.boot.annotation;

import io.eventuate.tram.spring.events.subscriber.TramEventSubscriberConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target( { ElementType.TYPE } )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Import( TramEventSubscriberConfiguration.class )
public @interface EnableTramEventSubscriber {
}
