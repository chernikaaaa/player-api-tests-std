package steps.player;

import api.player.PlayerApi;
import api.player.models.Player;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import utils.player.PlayerUtils;

public class PlayerSteps {

    private PlayerSteps() {
        throw new IllegalStateException("Utility class"); //TODO make baseutility and move it to it and reuse anywhere
    }

    @Step("Create player and validate success response")
    public static void createPlayerAndValidateSuccessResponse(String editor, Player newPlayer) {
        var createdPlayer = createPlayer(editor, newPlayer);

        PlayerAsserts.assertPlayer(createdPlayer, newPlayer);
        //TODO add check that id isnot in all players list
    }

    @Step("Create player") //todo add step more detailed description
    public static Player createPlayer(String editor, Player newPlayer) {
        var playerParams = PlayerUtils.buildMapParamsFromPlayerObject(newPlayer);
        return PlayerApi.create(editor, playerParams).statusCode(200).extract().as(Player.class);
    }

    @Step("Create player and validate bad request")
    public static void createPlayerAndValidatBadRequestMessage(String editor,
                                                               Player newPlayer,
                                                               String expectedMessage) {
        var response = createPlayerWithError(editor, newPlayer, 400);
        // but in  doc we don't have 400 error but we should handle incorrect data,
        // we have only 404 but it is for not founding resource for example when player with such login isn't founded

        var message = response.jsonPath().getString("message");

        ErrorAsserts.assertErroMessage(message, expectedMessage);
    }

    @Step("Create player with expected error")
    public static Response createPlayerWithError(String editor, Player newPlayer, int errorCode) {
        var playerParams = PlayerUtils.buildMapParamsFromPlayerObject(newPlayer);
        return PlayerApi.create(editor, playerParams).statusCode(errorCode).extract().response();
    }

}
