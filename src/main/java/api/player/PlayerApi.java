package api.player;

import api.Client;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class PlayerApi extends Client {

    @Step("Create user as {editor}")
    public static ValidatableResponse create(String editor, Map<String, ?> q) {
        return given()
                .spec(spec)
                .pathParam("editor", editor)
                .queryParams(q)
                .when()
                .get("/player/create/{editor}")
                .then();
    }

    @Step("Delete user as {editor}, id={playerId}")
    public Response delete(String editor, long playerId) {
        return given()
                .spec(spec)
                .pathParam("editor", editor)
                .body(Map.of("playerId", playerId))
                .when()
                .delete("/player/delete/{editor}")
                .then()
                .extract()
                .response();
    }

    @Step("Get user by id={playerId}")
    public Response get(long playerId) {
        return given()
                .spec(spec)
                .body(Map.of("playerId", playerId))
                .when()
                .post("/player/get")
                .then()
                .extract()
                .response();
    }

    @Step("Get all users")
    public Response getAll() {
        return given()
                .spec(spec).when().get("/player/get/all").then().extract().response();
    }

    @Step("Update user as {editor}, id={id}")
    public Response update(String editor, long id, Map<String, ?> body) {
        return given()
                .spec(spec)
                .pathParam("editor", editor)
                .pathParam("id", id)
                .body(body)
                .when()
                .patch("/player/update/{editor}/{id}")
                .then()
                .extract()
                .response();
    }

}
