package tests.player.delete;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DeletePlayerNegativeTests extends DeletePlayerBaseTest {

    @Test(description = "Failed delete by roles test", dataProvider = "loginAndRolesForFailedDelete")
    public void failedDeleteByRolesTest(String loginWhoDelete, Integer playerToDeleteId) {
        var resp = playerApi.delete(loginWhoDelete, playerToDeleteId);
        Assert.assertTrue(resp.statusCode() == 200, "delete status");
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
