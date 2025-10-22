package tests.base;

import api.player.PlayerApi;
import api.player.models.Player;
import enums.Role;
import helpers.players.PlayerCreationalHelpers;
import org.testng.annotations.BeforeClass;
import steps.player.PlayerSteps;
import utils.player.PlayerUtils;

import java.util.UUID;
import java.util.stream.Stream;

public class BasePlayerTest {

    protected Player randomAdmin;
    protected Player randomUser;
    protected PlayerApi playerApi = new PlayerApi();
    protected static final String SUPERVISOR_LOGIN = "supervisor";
    protected static final String ADMIN_LOGIN = "admin";

    @BeforeClass
    public void setupPreconditions() {
        randomAdmin = new Player(PlayerUtils.getRandomAge(),
                                 PlayerUtils.getRandomGender(),
                                 null,
                                 UUID.randomUUID().toString(),
                                 PlayerUtils.getRandomPassWithRandomLength(),
                                 Role.ADMIN.getRole(),
                                 UUID.randomUUID().toString());

        randomUser = PlayerCreationalHelpers.createSuccessRandomPlayer(Role.USER);

        Stream.of(randomAdmin, randomUser).forEach(player -> {
            PlayerSteps.createPlayer(SUPERVISOR_LOGIN, player);
            //TODO add waiter with check db that user is created (instead I use sleep but it is a bad practice)
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
