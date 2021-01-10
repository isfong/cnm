package io.eventuate.tram.spring.boot.annotation;

import io.eventuate.tram.sagas.spring.orchestration.SagaOrchestratorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target( { ElementType.TYPE } )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Import( SagaOrchestratorConfiguration.class )
public @interface EnableSagaOrchestrator {
}
