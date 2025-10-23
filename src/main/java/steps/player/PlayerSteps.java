package steps.player;

import api.player.PlayerApi;
import api.player.models.AllPlayersResponse;
import api.player.models.Player;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import utils.BaseUtility;
import utils.player.PlayerUtils;

import java.util.List;

public class PlayerSteps {

    private static final String MESSAGE = "message";

    private PlayerSteps() {
        BaseUtility.getException();
    }

    @Step("Create player and validate success response")
    public static Player createPlayerAndValidateSuccessResponse(String editor, Player newPlayer) {
        var createdPlayer = createPlayer(editor, newPlayer);

        PlayerAsserts.assertPlayerId(newPlayer);
        PlayerAsserts.assertPlayerDetails(createdPlayer, newPlayer);
        return createdPlayer;
    }

    @Step("Create player successfully")
    public static Player createPlayer(String editor, Player newPlayer) {
        var playerParams = PlayerUtils.buildMapParamsFromPlayerObject(newPlayer);
        return PlayerApi.create(editor, playerParams).statusCode(200).extract().as(Player.class);
    }

    @Step("Update player")
    public static Player updatePlayer(String editor, Integer id, Player udpatedPlayer) {
        var playerParams = PlayerUtils.buildMapParamsFromPlayerObject(udpatedPlayer);
        return PlayerApi.update(editor, id, playerParams).statusCode(200).extract().as(Player.class);
    }

    @Step("Update player failed with error")
    public static ValidatableResponse updatePlayerFailedWithError(String editor,
                                                                  Integer id,
                                                                  Player udpatedPlayer,
                                                                  Integer expectedStatusCode) {
        var playerParams = PlayerUtils.buildMapParamsFromPlayerObject(udpatedPlayer);
        return PlayerApi.update(editor, id, playerParams).statusCode(expectedStatusCode);
    }

    //TODO uncomment when 400 code and processing of data errors will be implemented
//    @Step("Update player failed with error 400 and message")
//    public static void updatePlayerFailedWithErrorAndMessage(String editor,
//                                                             Integer id,
//                                                             Player udpatedPlayer,
//                                                             String errorMessage) {
//        var response = updatePlayerFailedWithError(editor, id, udpatedPlayer, 400);
//        var message = response.extract().jsonPath().getString(MESSAGE);
//        ErrorAsserts.assertErrorMessage(message, errorMessage);
//    }

    //400 error isn't implemented in doc, but we should handle incorrect data
    @Step("Create player and validate bad request with error 400 and message")
    public static void createPlayerAndValidateBadRequestMessage(String editor,
                                                                Player newPlayer,
                                                                String expectedMessage) {
        var response = createPlayerWithError(editor, newPlayer, 400);
        // but in doc we don't have 400 error, but we should handle incorrect data,
        var message = response.jsonPath().getString(MESSAGE);
        ErrorAsserts.assertErrorMessage(message, expectedMessage);
    }

    @Step("Create player with expected error")
    public static Response createPlayerWithError(String editor, Player newPlayer, int errorCode) {
        var playerParams = PlayerUtils.buildMapParamsFromPlayerObject(newPlayer);
        return PlayerApi.create(editor, playerParams).statusCode(errorCode).extract().response();
    }

    //400 error isn't implemented in doc, but we should handle incorrect data
    @Step("Delete player with expected error 400 and message")
    public static void deletePlayerWithErrorAndMessage(String editor, Integer playerId, String expectedMessage) {
        var actualMessage =
                deletePlayerWithError(editor, playerId, 400).extract().response().jsonPath().getString(MESSAGE);
        Assert.assertEquals(actualMessage, expectedMessage, "Message should be as expected");
    }

    @Step("Get all players")
    public static List<AllPlayersResponse.AllPlayerResponseItem> getAllPlayers() {
        return PlayerApi.getAll().statusCode(200).extract().response().as(AllPlayersResponse.class).players();
    }

    @Step("Delete player with expected error")
    public static ValidatableResponse deletePlayerWithError(String editor,
                                                            Integer playerId,
                                                            Integer expectedStatusCode) {
        return PlayerApi.delete(editor, playerId).statusCode(expectedStatusCode);
    }

    @Step("Delete player with successfully")
    public static void deletePlayer(String editor, Integer playerId) {
        //TODO should return 200 not 204, or if it would be agreed with business 204 but need to delete 200 from spec
//        return PlayerApi.delete(editor, playerId).statusCode(200).extract().response().as(DeleteResponseEntity.class);
        PlayerApi.delete(editor, playerId).statusCode(204);
    }

    @Step("Get player with expected error")
    public static ValidatableResponse getPlayerWithError(Integer playerId, Integer expectedStatusCode) {
        return PlayerApi.get(playerId).statusCode(expectedStatusCode);
    }

    //400 error isn't implemented in doc, but we should handle incorrect data
    @Step("Get player with expected error 400 and message")
    public static void getPlayerWithErrorAndMessage(Integer playerId, String expectedMessage) {
        var actualMessage = getPlayerWithError(playerId, 400).extract().response().jsonPath().getString(MESSAGE);
        Assert.assertEquals(actualMessage, expectedMessage, "Message should be as expected");
    }

    @Step("Get player successfully")
    public static Player getPlayer(Integer playerId) {
        return PlayerApi.get(playerId).statusCode(200).extract().response().as(Player.class);
    }

}
