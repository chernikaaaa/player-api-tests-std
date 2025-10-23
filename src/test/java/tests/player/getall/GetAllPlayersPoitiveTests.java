package tests.player.getall;

import org.testng.annotations.Test;
import steps.player.PlayerAsserts;
import steps.player.PlayerSteps;
import tests.base.BasePlayerTest;

import java.util.List;

public class GetAllPlayersPoitiveTests extends BasePlayerTest {

    @Test(description = "Get all players returns list")
    public void getAllSuccessTest() {
        var allPlayers = PlayerSteps.getAllPlayers();
        PlayerAsserts.assertGetAllPlayersResponse(allPlayers, List.of(randomAdminId, randomUserId));
    }

}
