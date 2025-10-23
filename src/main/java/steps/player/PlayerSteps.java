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

    private PlayerSteps() {
        BaseUtility.getException();
    }

    @Step("Create player and validate success response")
    public static void createPlayerAndValidateSuccessResponse(String editor, Player newPlayer) {
        var createdPlayer = createPlayer(editor, newPlayer);

        PlayerAsserts.assertPlayerId(newPlayer);
        PlayerAsserts.assertPlayer(createdPlayer, newPlayer);
    }

    @Step("Create player") //TODO add step more detailed description
    public static Player createPlayer(String editor, Player newPlayer) {
        var playerParams = PlayerUtils.buildMapParamsFromPlayerObject(newPlayer);
        return PlayerApi.create(editor, playerParams).statusCode(200).extract().as(Player.class);
    }

    @Step("Update player")
    public static Player updatePlayer(String editor, Integer id, Player udpatedPlayer) {
        var playerParams = PlayerUtils.buildMapParamsFromPlayerObject(udpatedPlayer);
        return PlayerApi.update(editor, id, playerParams).statusCode(200).extract().as(Player.class);
    }

    @Step("Create player and validate bad request")
    public static void createPlayerAndValidateBadRequestMessage(String editor,
                                                                Player newPlayer,
                                                                String expectedMessage) {
        var response = createPlayerWithError(editor, newPlayer, 404);
        // but in  doc we don't have 404 error but we should handle incorrect data,
        // we have only 404 but it is for not founding resource for example when player with such login isn't founded

        var message = response.jsonPath().getString("message");

        ErrorAsserts.assertErroMessage(message, expectedMessage);
    }

    @Step("Create player with expected error")
    public static Response createPlayerWithError(String editor, Player newPlayer, int errorCode) {
        var playerParams = PlayerUtils.buildMapParamsFromPlayerObject(newPlayer);
        return PlayerApi.create(editor, playerParams).statusCode(errorCode).extract().response();
    }

    //TODO uncomment this when 404 code will be handled in api
//    @Step("Delete player with expected error and message")
//    public static void deletePlayerWithErrorAndMessage(String editor, Integer playerId, String expectedMessage) {
//        var actualMessage =
//                deletePlayerWithError(editor, playerId, 404).extract().response().jsonPath().getString("message");
//        Assert.assertEquals(actualMessage, expectedMessage, "Message should be as expected");
//    }

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
        //TODO should return 200 not 204, or if it would be agreeded with business 204 but need to delete 200 from spec
//        return PlayerApi.delete(editor, playerId).statusCode(200).extract().response().as(DeleteResponseEntity.class);
        PlayerApi.delete(editor, playerId).statusCode(204);
    }

    @Step("Get player with expected error")
    public static ValidatableResponse getWithError(Integer playerId,
                                                   Integer expectedStatusCode) {
        return PlayerApi.get(playerId).statusCode(expectedStatusCode);
    }

    @Step("Get player with expected error and message")
    public static void getWithErrorAndMessage(Integer playerId, String expectedMessage) {
        var actualMessage = getWithError(playerId, 404).extract().response().jsonPath().getString("message");
        Assert.assertEquals(actualMessage, expectedMessage, "Message should be as expected");
    }

    @Step("Get player with successfully")
    public static Player get(Integer playerId) {
        return PlayerApi.get(playerId).statusCode(200).extract().response().as(Player.class);
    }

}
