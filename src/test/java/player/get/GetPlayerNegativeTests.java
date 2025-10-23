package player.get;

import api.PlayerApi;
import org.testng.annotations.Test;
import player.BasePlayerTest;
import steps.player.PlayerSteps;

public class GetPlayerNegativeTests extends BasePlayerTest {

    @Test(description = "Cannot get unexisting user")
    public void canNotGetUnExistingTest() {
        PlayerSteps.getPlayerWithError(generateNextPlayerId(), 404);
    }

    @Test(description = "Cannot get without id")
    public void canNotGetWithoutIdTest() {
        PlayerApi.getWithoutBody().then().statusCode(400);
    }

    @Test(description = "Unauthorized user cannot delete  users")
    public void unauthorizedCannotDeleteUserTest() {
        //TODO cannot be deployed due to lack of unauthenticated access in api //401
    }

    @Test(description = "Forbidden user cannot delete  users")
    public void forbiddenCannotDeleteUserTest() {
        //TODO cannot be deployed due to lack of user mention in request //403
    }

}
