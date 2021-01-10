package io.eventuate.tram.spring.boot.annotation;

import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target( { ElementType.TYPE } )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Import( OptimisticLockingDecoratorConfiguration.class )
public @interface EnableOptimisticLockingDecorator {
}
