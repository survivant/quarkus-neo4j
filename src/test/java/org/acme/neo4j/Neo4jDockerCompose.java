package org.acme.neo4j;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;

public class Neo4jDockerCompose implements QuarkusTestResourceLifecycleManager {

    @SuppressWarnings("rawtypes")
    private static final DockerComposeContainer container = new DockerComposeContainer(new File("docker-compose-test.yml"))
            .withExposedService("quarkus", 8080,
                    Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)));

    @Override
    public Map<String, String> start() {
        container.start();

        return Collections.EMPTY_MAP;
    }

    @Override
    public void stop() {
        container.close();
    }
}
