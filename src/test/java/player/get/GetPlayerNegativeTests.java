package player.get;

import org.testng.annotations.Test;
import player.BasePlayerTest;
import steps.player.PlayerSteps;

public class GetPlayerNegativeTests extends BasePlayerTest {

    @Test(description = "Cannot get unexisting user")
    public void canNotGetUnExistingTest() {
        PlayerSteps.getWithErrorAndMessage(generateNextPlayerId(), "player not found error");
    }

    @Test(description = "Unauthorized user cannot delete  users")
    public void unauthorizedCannotDeleteUser() {
        //TODO cannot be deployed due to lack of unauthenticated access in api
    }

    @Test(description = "Forbidden user cannot delete  users")
    public void forbiddenCannotDeleteUser() {
        //TODO cannot be deployed due to lack of user mention in request
    }

}
