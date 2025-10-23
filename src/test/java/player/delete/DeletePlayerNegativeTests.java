package player.delete;

import api.player.models.Player;
import enums.Role;
import helpers.players.PlayerCreationalHelpers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import player.BasePlayerTest;
import steps.player.PlayerSteps;

public class DeletePlayerNegativeTests extends BasePlayerTest {

    private Player userWhoDelete;
    private Integer randomUserId2;

    @BeforeClass
    @Override
    protected void setupPreconditions() {
        super.setupPreconditions();

        userWhoDelete = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.USER);
        PlayerSteps.createPlayer(SUPERVISOR_LOGIN, userWhoDelete);

        var randomUser2 = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.USER);
        randomUserId2 = PlayerSteps.createPlayer(SUPERVISOR_LOGIN, randomUser2).id();

        //TODO add waiter with check db that user is created (instead I use sleep but it is a bad practice)

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Failed delete by roles test", dataProvider = "loginAndRolesForFailedDelete")
    public void failedDeleteByRolesTest(String loginWhoDelete, Integer playerToDeleteId) {
        PlayerSteps.deletePlayerWithError(loginWhoDelete, playerToDeleteId, 403);
    }

    @DataProvider
    public Object[][] loginAndRolesForFailedDelete() {
        return new Object[][]{
                {
                        userWhoDelete.login(),
                        randomAdminId
                },
                {
                        SUPERVISOR_LOGIN,
                        mainSupervisorId
                },
                {
                        ADMIN_LOGIN,
                        mainSupervisorId
                },
                {
                        ADMIN_LOGIN,
                        randomAdminId
                        //admin cannot delete another admin
                },
                {
                        randomUser.login(),
                        mainSupervisorId
                },
                {
                        randomUser.login(),
                        randomUserId2
                        //user cannot delete another user
                },
        };
    }

    @Test(description = "Unauthorized user cannot delete  users")
    public void unauthorizedCannotDeleteUser() {
        //TODO cannot be deployed due to lack of unauthenticated access in api
    }

    @Test(description = "Failed delete by incorrect id test")
    public void failedDeleteByIncorrectIdTest() {
        //TODO uncomment this when 404 code will be handled in api
//        PlayerSteps.deletePlayerWithErrorAndMessage(SUPERVISOR_LOGIN, generateNextPlayerId(), "Invalid player ID");
    }

    @Test(description = "Failed delete by incorrect format id test")
    public void failedDeleteByIncorrectFormatIdTest() {
        //TODO make method with inserting json to body not to model in order to use id as String
    }

}
