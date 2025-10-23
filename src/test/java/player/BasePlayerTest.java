package player;

import api.player.models.AllPlayersResponse;
import api.player.models.Player;
import enums.Role;
import helpers.players.PlayerCreationalHelpers;
import io.qameta.allure.Step;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import steps.player.PlayerSteps;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public abstract class BasePlayerTest {

    protected Player randomAdmin;
    protected Integer randomAdminId;
    protected Player randomUser;
    protected Integer randomUserId;
    protected static final String SUPERVISOR_LOGIN = "supervisor";
    protected static final String ADMIN_LOGIN = "admin";
    protected ArrayList<Integer> toDeletePlayerIds = new ArrayList<>();

    @BeforeClass
    protected void setupPreconditions() {
        randomAdmin = PlayerCreationalHelpers.createSuccessRandomAdminPlayer();
        randomAdminId = PlayerSteps.createPlayer(SUPERVISOR_LOGIN, randomAdmin).id();
        toDeletePlayerIds.add(randomAdminId);

        randomUser = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.USER);
        randomUserId = PlayerSteps.createPlayer(SUPERVISOR_LOGIN, randomUser).id();
        toDeletePlayerIds.add(randomUserId);

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

        while (PlayerSteps.getAllPlayers()
                          .stream()
                          .map(AllPlayersResponse.AllPlayerResponseItem::id)
                          .toList()
                          .contains(nextId)) {
            nextId += 10;
        }

        return nextId;
    }

    @Step("Get max player id")
    private static Integer getMaxPlayerId() {
        return PlayerSteps.getAllPlayers()
                          .stream()
                          .sorted(Comparator.comparing(AllPlayersResponse.AllPlayerResponseItem::id))
                          .collect(Collectors.toCollection(LinkedList::new))
                          .getLast()
                          .id();
    }

    @AfterSuite(alwaysRun = true)
    public void cleanupCreatedPlayers() {
        toDeletePlayerIds.forEach(toDeletePlayerId -> {
            PlayerSteps.deletePlayer(SUPERVISOR_LOGIN, toDeletePlayerId);
            //TODO add check that all players are deleted from db
        });
    }

}
