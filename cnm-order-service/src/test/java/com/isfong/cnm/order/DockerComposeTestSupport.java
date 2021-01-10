package com.isfong.cnm.order;

import com.github.javafaker.Faker;
import com.palantir.docker.compose.DockerComposeExtension;
import com.palantir.docker.compose.configuration.DockerComposeFiles;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Locale;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE )
public class DockerComposeTestSupport {


    @SuppressWarnings( { "unused", "RedundantSuppression" } )
    @RegisterExtension
    public static final DockerComposeExtension dockerComposeExtension = DockerComposeExtension.builder( )
            .files( DockerComposeFiles.from( "src/test/resources/docker-compose.yml" ) )
            .waitingForService( "zookeeper", HealthChecks.toHaveAllPortsOpen( ) )
            .waitingForService( "kafka", HealthChecks.toHaveAllPortsOpen( ) )
            .waitingForService( "postgres-order-service", HealthChecks.toHaveAllPortsOpen( ) )
            .waitingForService( "cdc-service", HealthChecks.toHaveAllPortsOpen( ) )
//            .shutdownStrategy( ShutdownStrategy.SKIP )
            .build( );


    @Autowired
    TestRestTemplate rest;
    @Value( "${local.server.port}" )
    protected int port;

    protected Faker faker = Faker.instance( Locale.CHINA );

    protected String url( @SuppressWarnings( "SameParameterValue" ) String basePath ) {
        return "http://localhost:" + port + basePath;
    }

}
