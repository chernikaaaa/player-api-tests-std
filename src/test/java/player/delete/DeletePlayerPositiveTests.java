package player.delete;

import enums.Role;
import helpers.players.PlayerCreationalHelpers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import player.BasePlayerTest;
import steps.player.PlayerSteps;

public class DeletePlayerPositiveTests extends BasePlayerTest {

    private Integer adminForDeleteId;
    private Integer adminForDelete2Id;
    private String adminForDelete2Login;
    private Integer userForDeleteId;
    private Integer userForDelete2Id;
    private String userForDelete2Login;

    @BeforeClass
    @Override
    protected void setupPreconditions() {
        super.setupPreconditions();

        var adminForDelete = PlayerCreationalHelpers.createSuccessRandomAdminPlayer();
        adminForDeleteId = PlayerSteps.createPlayer(SUPERVISOR_LOGIN, adminForDelete).id();

        var adminForDelete2 = PlayerCreationalHelpers.createSuccessRandomAdminPlayer();
        var adminForDelete2RespPlayer = PlayerSteps.createPlayer(SUPERVISOR_LOGIN, adminForDelete2);
        adminForDelete2Id = adminForDelete2RespPlayer.id();
        adminForDelete2Login = adminForDelete2RespPlayer.login();

        var userForDelete = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.USER);
        userForDeleteId = PlayerSteps.createPlayer(SUPERVISOR_LOGIN, userForDelete).id();

        var userForDelete2 = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.USER);
        var userForDelete2RespPlayer = PlayerSteps.createPlayer(SUPERVISOR_LOGIN, userForDelete2);
        userForDelete2Id = userForDelete2RespPlayer.id();
        userForDelete2Login = userForDelete2RespPlayer.login();
    }

    @Test(description = "Successful delete by roles test", dataProvider = "loginAndRolesForSuccessfulDelete")
    public void successfulDeleteByRolesTest(String loginWhoDelete, Integer playerToDeleteId) {
        PlayerSteps.deletePlayer(loginWhoDelete, playerToDeleteId);
        //TODO add check that player is really deleted in db
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
