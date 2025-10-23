package tests.player.delete;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.player.PlayerSteps;

public class DeletePlayerNegativeTests extends DeletePlayerBaseTest {

    @Test(description = "Failed delete by roles test", dataProvider = "loginAndRolesForFailedDelete")
    public void failedDeleteByRolesTest(String loginWhoDelete, Integer playerToDeleteId) {
        PlayerSteps.deletePlayer(loginWhoDelete, playerToDeleteId);
        //TODO add assertions that there isn't this player in db
    }

    @DataProvider
    public Object[][] loginAndRolesForFailedDelete() {
        return new Object[][]{
                {
                        userWhoDelete.login(),
                        randomAdmin.id()
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
