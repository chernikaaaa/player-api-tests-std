package api.player;

import Endpoints.Endpoints;
import api.BaseClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class PlayerApi extends BaseClient {

    private static final String EDITOR_PARAM = "editor";
    private static final String PLAYER_ID = "playerId";
    private static final Logger log = LoggerFactory.getLogger(PlayerApi.class);

    @Step("Create user as {editor}")
    public static ValidatableResponse create(String editor, Map<String, ?> queryParams) {
        log.info("Creating user as {}, with params: {}", editor, queryParams);
        return given()
                .spec(spec)
                .pathParam(EDITOR_PARAM, editor)
                .queryParams(queryParams)
                .when()
                .get(Endpoints.CREATE_PLAYER)
                .then();
    }

    @Step("Delete user as {editor}, id={playerId}")
    public static ValidatableResponse delete(String editor, Integer playerId) {
        log.info("Deleting user as {}, id={}", editor, playerId);
        return given()
                .spec(spec)
                .pathParam(EDITOR_PARAM, editor)
                .body(Map.of(PLAYER_ID, playerId))
                .when()
                .delete(Endpoints.DELETE_PLAYER)
                .then();
    }

    @Step("Get user by id={playerId}")
    public static ValidatableResponse get(Integer playerId) {
        log.info("Getting user by id={}", playerId);
        return given()
                .spec(spec)
                .body(Map.of(PLAYER_ID, playerId))
                .when()
                .post(Endpoints.GET_PLAYER)
                .then();
    }

    @Step("Get user without body")
    public static Response getWithoutBody() {
        log.info("Getting without body");
        return given()
                .spec(spec)
                .when()
                .post(Endpoints.GET_PLAYER);
    }

    @Step("Get all users")
    public static ValidatableResponse getAll() {
        log.info("Getting all users");
        return given().spec(spec).when().get("/player/get/all").then();
    }

    @Step("Update user as {editor}, id={id}")
    public static ValidatableResponse update(String editor, Integer id, Map<String, ?> body) {
        log.info("Updating user as {}, id={}, body={}", editor, id, body);
        return given()
                .spec(spec)
                .pathParam(EDITOR_PARAM, editor)
                .pathParam("id", id)
                .body(body)
                .when()
                .patch(Endpoints.GET_ALL_PLAYERS)
                .then();
    }

}
