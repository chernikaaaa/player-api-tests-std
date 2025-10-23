package tests.player.create;

import enums.Role;
import helpers.players.PlayerCreationalHelpers;
import org.testng.annotations.Test;
import steps.player.PlayerSteps;
import tests.base.BasePlayerTest;

public class CreatePlayerAccessesNegativeTests extends BasePlayerTest {

    @Test(description = "User cannot create new users")
    public void userCanNotCreateUser() {
        var newPlayer = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.USER);
        PlayerSteps.createPlayerWithError(randomUser.login(), newPlayer, 403);
    }

    @Test(description = "Admin cannot create supervisor")
    public void adminCanNotCreateSupervisor() {
        var newPlayer = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.SUPERVISOR);
        PlayerSteps.createPlayerAndValidatBadRequestMessage(ADMIN_LOGIN,
                                                            newPlayer,
                                                            "some message about role validation");
    }

    @Test(description = "Unauthorized user cannot create new users")
    public void unauthorizedCannotCreateUser() {
        //TODO cannot be deployed due to lack of unauthenticated access in api
    }

}
