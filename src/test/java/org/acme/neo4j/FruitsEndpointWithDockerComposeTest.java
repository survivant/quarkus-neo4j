package org.acme.neo4j;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

@QuarkusTest
@QuarkusTestResource(Neo4jDockerCompose.class)
class FruitsEndpointWithDockerComposeTest {

    // https://www.testcontainers.org/modules/docker_compose/

    private static final Map<String, String> fruitIdMap = new HashMap<>();
    private static final UnaryOperator<String> FRUIT = fruit ->  String.format("{\"name\":\"%s\"}", fruit);

    @Test
    public void testResource() {
        String cherry = "Cherry";
        String pear = "Pear";
        String apple = "Apple";

        // Load initial data:
        Arrays.asList(cherry, pear, apple).forEach(fruit -> {
                String location = given()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .body(FRUIT.apply(fruit))
                    .when().post("/fruits")
                    .then()
                    .statusCode(201)
                    .extract().header("Location");
                fruitIdMap.put(fruit, location);
            }
        );

        //List all, should have all 3 fruits after initial data import:
        given()
            .when().get("/fruits")
            .then()
            .statusCode(200)
            .body(
                containsString(cherry),
                containsString(apple),
                containsString(pear)
            );

        //Delete Cherry
        given()
            .when().delete(fruitIdMap.get(cherry))
            .then()
            .statusCode(204);

        //Cherry is missing now (with reactive):
        given()
            .when().get("/reactivefruits")
            .then()
            .statusCode(200)
            .body(
                not(containsString(cherry)),
                containsString(apple),
                containsString(pear)
            );
    }
}
