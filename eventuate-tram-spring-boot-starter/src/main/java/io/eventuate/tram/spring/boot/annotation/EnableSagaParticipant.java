package io.eventuate.tram.spring.boot.annotation;

import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target( { ElementType.TYPE } )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Import( SagaParticipantConfiguration.class )
public @interface EnableSagaParticipant {
}
