package tests.player.get;

import org.testng.annotations.Test;
import steps.player.PlayerSteps;
import tests.base.BasePlayerTest;

public class GetPlayerPositiveTests extends BasePlayerTest {

    @Test(description = "Get existing user successfully")
    public void getUserSuccessfullTest() {
        PlayerSteps.get(randomAdminId);
    }

    //TODO delete 201 code created from spec
}
