package tests.player.delete;

import api.player.models.Player;
import enums.Role;
import helpers.players.PlayerCreationalHelpers;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.player.PlayerSteps;
import tests.base.BasePlayerTest;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class DeletePlayerNegativeTests extends BasePlayerTest {

    private Player userWhoDelete;
    private Integer randomUserId2;
    private Integer mainSupervisorId = 1;

    @BeforeClass
    @Override
    protected void setupPreconditions() {
        super.setupPreconditions();

        //todo uncomment under code when roles and logins will be present in get all response
//        mainSupervisorId = PlayerApi.getAll().as(AllPlayersResponse.class).getPlayers()
//                                          .stream()
//                                          .filter(player -> player.login().equals(SUPERVISOR_LOGIN))
//                                          .findFirst()
//                                          .orElseThrow(() -> new NoSuchElementException("No supervisor found"))
//                                          .id();

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
        //todo cannot be deployed due to lack of unauthenticated access in api
    }

    @Test(description = "Failed delete by incorrect id test")
    public void failedDeleteByIncorrectIdTest() {
        PlayerSteps.deletePlayerWithErrorAndMessage(SUPERVISOR_LOGIN, generateNextPlayerId(), "Invalid player ID");
    }

    @Test(description = "Failed delete by incorrect format id test")
    public void failedDeleteByIncorrectFormatIdTest() {
        //TODO make method with inserting json to body not to model in order to use id as String
    }

    @Step("Generate next player id")
    private static int generateNextPlayerId() {
        var nextId = getMaxPlayerId() + 10;

        while (PlayerSteps.getAllPlayers().stream().map(item -> item.id()).toList().contains(nextId)) {
            nextId += 10;
        }

        return nextId;
    }

    @Step("Get max player id")
    private static Integer getMaxPlayerId() {
        return PlayerSteps.getAllPlayers()
                          .stream()
                          .sorted(Comparator.comparing(player -> player.id()))
                          .collect(Collectors.toCollection(LinkedList::new))
                          .getLast()
                          .id();
    }

}
