package io.eventuate.tram.spring.boot.annotation;

import io.eventuate.tram.spring.boot.autoconfigure.DefaultEventuateSqlDialect_0_12_0_RELEASEFixConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target( { ElementType.TYPE } )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Import( DefaultEventuateSqlDialect_0_12_0_RELEASEFixConfiguration.class )
public @interface EnableDefaultEventuateSqlDialect_0_12_0_RELEASEFix {
}
