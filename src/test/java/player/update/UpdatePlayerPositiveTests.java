package player.update;

import api.player.models.Player;
import helpers.players.PlayerCreationalHelpers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import player.BasePlayerTest;
import steps.player.PlayerAsserts;
import steps.player.PlayerSteps;

public class UpdatePlayerPositiveTests extends BasePlayerTest {

    @Test(description = "successfully update player")
    public void successfullyUpdatePlayerTest() {
        var expectedPlayer = PlayerCreationalHelpers.withAge(randomAdmin, randomAdmin.age() + 2);
        var actualPlayer = PlayerSteps.updatePlayer(SUPERVISOR_LOGIN, randomAdminId, expectedPlayer);

        PlayerAsserts.assertIdsEquals(actualPlayer, randomAdminId);
        PlayerAsserts.assertPlayerDetails(actualPlayer, expectedPlayer);
    }

    @Test(description = "successfully update player by roles", dataProvider = "loginAndRolesForSuccessfulUpdate")
    public void successfullyUpdatePlayerByRolesTest(String loginWhoUpdate,
                                                    Integer playerToUpdateId,
                                                    Player playerToUpdate) {
        var expectedPlayer = PlayerCreationalHelpers.withAge(playerToUpdate, playerToUpdate.age() + 2);
        PlayerSteps.updatePlayer(loginWhoUpdate, playerToUpdateId, expectedPlayer);
    }

    @DataProvider
    public Object[][] loginAndRolesForSuccessfulUpdate() {
        return new Object[][]{
                {
                        randomAdmin.login(),
                        randomAdminId,
                        randomAdmin
                },
                {
                        randomAdmin.login(),
                        randomUserId,
                        randomUser
                },
                {
                        randomUser.login(),
                        randomUserId,
                        randomUser
                },
        };
    }

}
