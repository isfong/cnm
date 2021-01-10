package io.eventuate.tram.spring.boot.annotation;

import io.eventuate.tram.spring.consumer.common.TramNoopDuplicateMessageDetectorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target( { ElementType.TYPE } )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Import( TramNoopDuplicateMessageDetectorConfiguration.class )
public @interface EnableTramNoopDuplicateMessageDetector {
}
