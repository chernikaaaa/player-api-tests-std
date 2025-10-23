package player.update;

import api.player.models.Player;
import enums.Role;
import helpers.players.PlayerCreationalHelpers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import player.BasePlayerTest;
import steps.player.PlayerSteps;

public class UpdatePlayerNegativeTests extends BasePlayerTest {

    private Player randomUser2;
    private Integer randomUser2Id;

    @BeforeClass
    @Override
    protected void setupPreconditions() {
        super.setupPreconditions();

        randomUser2 = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.USER);
        randomUser2Id = PlayerSteps.createPlayer(SUPERVISOR_LOGIN, randomUser2).id();
        toDeletePlayerIds.add(randomUser2Id);

        //TODO add waiter with check db that user is created (instead I use sleep but it is a bad practice)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Failed update player by roles")
    public void failedUpdatePlayerByRolesTest() {
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
                        randomUser.login(),
                        randomUser2Id,
                        randomUser2
                },
        };
    }

}
