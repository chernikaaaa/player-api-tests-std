package player.create;

import enums.Role;
import helpers.players.PlayerCreationalHelpers;
import org.testng.annotations.Test;
import player.BasePlayerTest;
import steps.player.PlayerSteps;

public class CreatePlayerAccessesNegativeTests extends BasePlayerTest {

    @Test(description = "User cannot create new users")
    public void userCanNotCreateUserTest() {
        var newPlayer = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.USER);
        PlayerSteps.createPlayerWithError(randomUser.login(), newPlayer, 403);
    }

    @Test(description = "Admin cannot create supervisor")
    public void adminCanNotCreateSupervisorTest() {
        var newPlayer = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.SUPERVISOR);
        PlayerSteps.createPlayerWithError(ADMIN_LOGIN, newPlayer, 403);
    }

    @Test(description = "Unauthorized user cannot create new users")
    public void unauthorizedCannotCreateUserTest() {
        //TODO cannot be deployed due to lack of unauthenticated access in api //401 error
    }

}
