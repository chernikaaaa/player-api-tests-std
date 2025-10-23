package player.get;

import org.testng.annotations.Test;
import player.BasePlayerTest;
import steps.player.PlayerAsserts;
import steps.player.PlayerSteps;

public class GetPlayerPositiveTests extends BasePlayerTest {

    @Test(description = "Get existing user successfully")
    public void getUserSuccessfulTest() {
        var returnedPlayer = PlayerSteps.getPlayer(randomAdminId);
        PlayerAsserts.assertPlayerDetails(returnedPlayer, randomAdmin);
    }

    //TODO delete 201 code mentioned in spec

    @Test(description = "Check response template")
    public void responseTemplateTest() {
        //TODO
    }

}
