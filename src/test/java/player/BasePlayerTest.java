package player;

import api.player.models.Player;
import enums.Role;
import helpers.players.PlayerCreationalHelpers;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeClass;
import steps.player.PlayerSteps;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class BasePlayerTest {

    protected Player randomAdmin;
    protected Integer randomAdminId;
    protected Player randomUser;
    protected Integer randomUserId;
    protected static final String SUPERVISOR_LOGIN = "supervisor";
    protected static final String ADMIN_LOGIN = "admin";

    @BeforeClass
    protected void setupPreconditions() {
        randomAdmin = PlayerCreationalHelpers.createSuccessRandomAdminPlayer();
        randomAdminId = PlayerSteps.createPlayer(SUPERVISOR_LOGIN, randomAdmin).id();
        randomUser = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.USER);
        randomUserId = PlayerSteps.createPlayer(SUPERVISOR_LOGIN, randomUser).id();

        //TODO add waiter with check db that user is created (instead I use sleep but it is a bad practice)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Generate next player id")
    protected static int generateNextPlayerId() {
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
