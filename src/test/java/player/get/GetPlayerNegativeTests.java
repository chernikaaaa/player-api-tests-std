package player.get;

import org.testng.annotations.Test;
import player.BasePlayerTest;
import steps.player.PlayerSteps;

public class GetPlayerNegativeTests extends BasePlayerTest {

    @Test(description = "Cannot get unexisting user")
    public void canNotGetUnExistingTest() {
        PlayerSteps.getPlayerWithErrorAndMessage(generateNextPlayerId(), "player not found error");
    }

    @Test(description = "Unauthorized user cannot delete  users")
    public void unauthorizedCannotDeleteUserTest() {
        //TODO cannot be deployed due to lack of unauthenticated access in api
    }

    @Test(description = "Forbidden user cannot delete  users")
    public void forbiddenCannotDeleteUserTest() {
        //TODO cannot be deployed due to lack of user mention in request
    }

}
