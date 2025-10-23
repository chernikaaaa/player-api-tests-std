package tests.base;

import api.player.PlayerApi;
import api.player.models.Player;
import enums.Role;
import helpers.players.PlayerCreationalHelpers;
import org.testng.annotations.BeforeClass;
import steps.player.PlayerSteps;

public class BasePlayerTest {

    protected Player randomAdmin;
    protected Integer randomAdminId;
    protected Player randomUser;
    protected PlayerApi playerApi = new PlayerApi();
    protected static final String SUPERVISOR_LOGIN = "supervisor";
    protected static final String ADMIN_LOGIN = "admin";

    @BeforeClass
    protected void setupPreconditions() {
        randomAdmin = PlayerCreationalHelpers.createSuccessRandomAdminPlayer();
        randomAdminId = PlayerSteps.createPlayer(SUPERVISOR_LOGIN, randomAdmin).id();
        randomUser = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.USER);
        PlayerSteps.createPlayer(SUPERVISOR_LOGIN, randomUser);

        //TODO add waiter with check db that user is created (instead I use sleep but it is a bad practice)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
