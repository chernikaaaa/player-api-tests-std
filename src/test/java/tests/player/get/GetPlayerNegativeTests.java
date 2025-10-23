package tests.player.get;

import org.testng.annotations.Test;
import steps.player.PlayerSteps;
import tests.base.BasePlayerTest;

public class GetPlayerNegativeTests extends BasePlayerTest {

    @Test(description = "Cannot get unexisting user")
    public void canNotGetUnExistingTest() {
        PlayerSteps.getWithErrorAndMessage(generateNextPlayerId(), "player not found error");
    }

    @Test(description = "Unauthorized user cannot delete  users")
    public void unauthorizedCannotDeleteUser() {
        //todo cannot be deployed due to lack of unauthenticated access in api
    }

    @Test(description = "Forbidden user cannot delete  users")
    public void forbiddenCannotDeleteUser() {
        //todo cannot be deployed due to lack of user mention in request
    }

    //TODO also there should be such tests if we would have user authoization and authication
    // admin can't get by id supervisor and another admin
    // user can't get by id admin supervisor and another user
}
