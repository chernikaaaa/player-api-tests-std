package tests.player.delete;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.player.PlayerSteps;

public class DeletePlayerPositiveTests extends DeletePlayerBaseTest {

    @Test(description = "Successful delete by roles test", dataProvider = "loginAndRolesForSuccessfulDelete")
    public void successfulDeleteByRolesTest(String loginWhoDelete, Integer playerToDeleteId) {
        PlayerSteps.deletePlayer(loginWhoDelete, playerToDeleteId);
        //todo add check that player is really deleted in db
    }

    @DataProvider
    public Object[][] loginAndRolesForSuccessfulDelete() {
        return new Object[][]{
                {
                        SUPERVISOR_LOGIN,
                        adminForDeleteId
                },
                {
                        adminForDelete2Login,
                        adminForDelete2Id
                },
                {
                        ADMIN_LOGIN,
                        userForDeleteId
                },
                {
                        userForDelete2Login,
                        userForDelete2Id
                },
        };
    }

}
