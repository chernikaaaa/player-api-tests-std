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

    // TODO Updating supervisor or main admin can potentially break the system,
    //  so these scenarios should be tested but on dev

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
        PlayerSteps.updatePlayerFailedWithError(SUPERVISOR_LOGIN, randomAdminId, expectedPlayer, 403);
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

    //TODO uncomment when 400 code and processing of data errors will be implemented
//    @Test(description = "Update player with non-existing ID should return 404")
//    public void updateNonExistingPlayerIdTest() {
//        int randomId = generateNextPlayerId();
//        var newData = PlayerCreationalHelpers.withLogin(randomUser, "updatedLogin_" + System.currentTimeMillis());
//        PlayerSteps.updatePlayerFailedWithErrorAndMessage(SUPERVISOR_LOGIN, randomId, newData, "Incorrect id");
//    }

    // TODO uncomment to test on dev, because changing login or id can break the system
//    @Test(description = "Update player with changed login should fail")
//    public void updatePlayerWithChangedLoginTest() {
//        var modifiedPlayer = PlayerCreationalHelpers.withLogin(randomUser2, UUID.randomUUID().toString());
//
//        PlayerSteps.updatePlayerFailedWithErrorAndMessage(SUPERVISOR_LOGIN,
//                                                          randomUser2Id,
//                                                          modifiedPlayer,
//                                                          "Login should not be modified");
//    }
//
//    @Test(description = "Update player with changed id should fail")
//    public void updatePlayerWithChangedIdTest() {
//        var nextPlayerId = generateNextPlayerId();
//        var modifiedPlayer = PlayerCreationalHelpers.withId(randomUser2, nextPlayerId);
//
//        PlayerSteps.updatePlayerFailedWithErrorAndMessage(SUPERVISOR_LOGIN,
//                                                          randomUser2Id,
//                                                          modifiedPlayer,
//                                                          "Id should not be modified");
//    }
}
