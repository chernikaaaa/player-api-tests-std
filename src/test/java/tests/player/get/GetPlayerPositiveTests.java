package tests.player.get;

import org.testng.annotations.Test;
import steps.player.PlayerAsserts;
import steps.player.PlayerSteps;
import tests.base.BasePlayerTest;

public class GetPlayerPositiveTests extends BasePlayerTest {

    @Test(description = "Get existing user successfully")
    public void getUserSuccessfullTest() {
        var returnedPlayer = PlayerSteps.get(randomAdminId);
        PlayerAsserts.assertPlayer(returnedPlayer, randomAdmin);
    }

    //TODO delete 201 code created from spec
}
