package tests.player.update;

import api.player.models.Player;
import enums.Role;
import helpers.players.PlayerCreationalHelpers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.player.PlayerAsserts;
import steps.player.PlayerSteps;
import tests.base.BasePlayerTest;

public class UpdatePlayerTests extends BasePlayerTest {

    private Player randomUser2;
    private Integer randomUser2Id;

    @BeforeClass
    @Override
    protected void setupPreconditions() {
        super.setupPreconditions();

        randomUser2 = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.USER);
        randomUser2Id = PlayerSteps.createPlayer(SUPERVISOR_LOGIN, randomUser2).id();

        //TODO add waiter with check db that user is created (instead I use sleep but it is a bad practice)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Succesfully update player")
    public void succesfullyUpdatePlayerTest() {
        var expectedPlayer = PlayerCreationalHelpers.withAge(randomAdmin, randomAdmin.age() + 2);
        var actualPlayer = PlayerSteps.updatePlayer(SUPERVISOR_LOGIN, randomAdminId, expectedPlayer);

        PlayerAsserts.assertIdsEquals(actualPlayer, randomAdminId);
        PlayerAsserts.assertPlayer(actualPlayer, expectedPlayer);
    }

    @Test(description = "Succesfully update player by roles", dataProvider = "loginAndRolesForSuccessfulUpdate")
    public void succesfullyUpdatePlayerByRolesTest(String loginWhoUpdate,
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
                {
                        SUPERVISOR_LOGIN,
                        mainSupervisorId,
                        PlayerCreationalHelpers.withId(PlayerCreationalHelpers.createSuccessRandomPlayer(Role.SUPERVISOR),
                                                       mainSupervisorId)
                },
        };
    }

    @Test(description = "Failed update player by roles")
    public void failedUpdatePlayeByRolesTest() {
        var expectedPlayer = PlayerCreationalHelpers.withAge(randomAdmin, randomAdmin.age() + 2);
        PlayerSteps.updatePlayer(SUPERVISOR_LOGIN, randomAdminId, expectedPlayer);
    }

    @DataProvider
    public Object[][] loginAndRolesForFailedUpdate() {
        return new Object[][]{
                {
                        ADMIN_LOGIN,
                        randomAdminId,
                        randomAdmin
                },
                {
                        ADMIN_LOGIN,
                        mainSupervisorId,
                        PlayerCreationalHelpers.withId(PlayerCreationalHelpers.createSuccessRandomPlayer(Role.SUPERVISOR),
                                                       mainSupervisorId)
                },
                {
                        randomUser.login(),
                        mainSupervisorId,
                        PlayerCreationalHelpers.withId(PlayerCreationalHelpers.createSuccessRandomPlayer(Role.SUPERVISOR),
                                                       mainSupervisorId)
                },
                {
                        randomUser.login(),
                        randomUser2Id,
                        randomUser2
                },
        };
    }

}
