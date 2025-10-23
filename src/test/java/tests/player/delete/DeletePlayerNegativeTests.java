package tests.player.delete;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.player.PlayerSteps;

public class DeletePlayerNegativeTests extends DeletePlayerBaseTest {

    @Test(description = "Failed delete by roles test", dataProvider = "loginAndRolesForFailedDelete")
    public void failedDeleteByRolesTest(String loginWhoDelete, Integer playerToDeleteId) {
        PlayerSteps.deletePlayerWithError(loginWhoDelete, playerToDeleteId, 403);
    }

    @Test(description = "Unauthorized user cannot delete  users")
    public void unauthorizedCannotDeleteUser() {
        //todo cannot be deployed due to lack of unauthenticated access in api
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
                        randomUser.login(),
                        mainSupervisorId
                },
        };
    }

}
