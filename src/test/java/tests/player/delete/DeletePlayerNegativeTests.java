package tests.player.delete;

import io.qameta.allure.Step;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.player.PlayerSteps;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class DeletePlayerNegativeTests extends DeletePlayerBaseTest {

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
                        randomUser.login(),
                        mainSupervisorId
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
