package tests.player.delete;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.player.PlayerSteps;

public class DeletePlayerPositiveTests extends DeletePlayerBaseTest {

    @Test(description = "Successful delete by roles test", dataProvider = "loginAndRolesForSuccessfulDelete")
    public void successfulDeleteByRolesTest(String loginWhoDelete, Integer playerToDeleteId) {
        PlayerSteps.deletePlayerWithError(loginWhoDelete, playerToDeleteId, "some error message");
    }

    @DataProvider
    public Object[][] loginAndRolesForSuccessfulDelete() {
        return new Object[][]{
                {
                        SUPERVISOR_LOGIN,
                        adminForDeleteId
                },
                {
                        ADMIN_LOGIN,
                        adminForDelete2Id
                },
                {
                        ADMIN_LOGIN,
                        userForDeleteId
                },
                {
                        userWhoDelete.login(),
                        userForDelete2Id
                },
        };
    }

}
