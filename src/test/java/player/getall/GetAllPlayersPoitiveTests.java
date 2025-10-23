package player.getall;

import org.testng.annotations.Test;
import player.BasePlayerTest;
import steps.player.PlayerAsserts;
import steps.player.PlayerSteps;

import java.util.List;

public class GetAllPlayersPoitiveTests extends BasePlayerTest {

    @Test(description = "Get all players returns list")
    public void getAllSuccessTest() {
        var allPlayers = PlayerSteps.getAllPlayers();
        PlayerAsserts.assertGetAllPlayersResponse(allPlayers, List.of(randomAdminId, randomUserId));
    }

    @Test(description = "Check response template")
    public void responseTemplateTest() {
        //TODO
    }

}
