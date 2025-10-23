package tests.player.delete;

import api.player.models.Player;
import enums.Role;
import helpers.players.PlayerCreationalHelpers;
import org.testng.annotations.BeforeClass;
import steps.player.PlayerSteps;
import tests.base.BasePlayerTest;

public class DeletePlayerBaseTest extends BasePlayerTest {

    protected Integer adminForDeleteId;
    protected Integer adminForDelete2Id;
    protected Integer userForDeleteId;
    protected Player userWhoDelete;
    protected Integer userForDelete2Id;
    protected Integer mainSupervisorId;

    @BeforeClass
    @Override
    protected void setupPreconditions() {
        super.setupPreconditions();

        mainSupervisorId = 1; //todo uncomment under code when roles and logins will be present in get all response
//        PlayerApi.getAll().as(AllPlayersResponse.class).getPlayers()
//                                          .stream()
//                                          .filter(player -> player.login().equals(SUPERVISOR_LOGIN))
//                                          .findFirst()
//                                          .orElseThrow(() -> new NoSuchElementException("No supervisor found"))
//                                          .id();

        var adminForDelete = PlayerCreationalHelpers.createSuccessRandomAdminPlayer();
        adminForDeleteId = PlayerSteps.createPlayer(SUPERVISOR_LOGIN, adminForDelete).id();
        var adminForDelete2 = PlayerCreationalHelpers.createSuccessRandomAdminPlayer();
        adminForDelete2Id = PlayerSteps.createPlayer(SUPERVISOR_LOGIN, adminForDelete2).id();
        var userForDelete = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.USER);
        userForDeleteId = PlayerSteps.createPlayer(SUPERVISOR_LOGIN, userForDelete).id();
        userWhoDelete = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.USER);
        PlayerSteps.createPlayer(SUPERVISOR_LOGIN, userWhoDelete).id();
        var userForDelete2 = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.USER);
        userForDelete2Id = PlayerSteps.createPlayer(SUPERVISOR_LOGIN, userForDelete2).id();

        //TODO add waiter with check db that user is created (instead I use sleep but it is a bad practice)

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
