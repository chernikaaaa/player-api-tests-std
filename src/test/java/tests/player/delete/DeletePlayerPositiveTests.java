package tests.player.delete;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DeletePlayerPositiveTests extends DeletePlayerBaseTest {

    @Test(description = "Successful delete by roles test", dataProvider = "loginAndRolesForSuccessfulDelete")
    public void successfulDeleteByRolesTest(String loginWhoDelete, Integer playerToDeleteId) {
        var resp = playerApi.delete(loginWhoDelete, playerToDeleteId);
        Assert.assertTrue(resp.statusCode() == 400, "delete status");
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
