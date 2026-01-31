package com.fulfilment.application.monolith.products;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class ProductResourceTest {

    @Test
    void shouldCreateAndGetProduct() {
        // Create a new product
        Long id = given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Laptop\",\"description\":\"Gaming laptop\",\"price\":1299.99,\"stock\":5}")
                .when().post("/product")
                .then()
                .statusCode(201)
                .body("name", equalTo("Laptop"))
                .extract().jsonPath().getLong("id");

        // Get the product by id
        given()
                .when().get("/product/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo("Laptop"))
                .body("description", equalTo("Gaming laptop"))
                .body("price", equalTo(1299.99f))
                .body("stock", equalTo(5));
    }

    @Test
    void shouldUpdateProduct() {
        // Create product first
        Long id = given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Chair\",\"description\":\"Office chair\",\"price\":199.99,\"stock\":10}")
                .when().post("/product")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        // Update product
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Chair Updated\",\"description\":\"Ergonomic chair\",\"price\":249.99,\"stock\":15}")
                .when().put("/product/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo("Chair Updated"))
                .body("description", equalTo("Ergonomic chair"))
                .body("price", equalTo(249.99f))
                .body("stock", equalTo(15));
    }


    @Test
    void shouldReturnErrorWhenIdIsSetOnCreate() {
        // Try to create with an id set
        given()
                .contentType(ContentType.JSON)
                .body("{\"id\":99,\"name\":\"Invalid\"}")
                .when().post("/product")
                .then()
                .statusCode(422)
                .body("error", containsString("Id was invalidly set"));
    }

}