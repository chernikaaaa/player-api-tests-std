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

    //TODO also there should be such tests if we would have user authoization and authication
    // supervisor can get by id supervisor
    // admin can get by id user and admin (him/herself)
    // user can get user (him/herself)
}
