package player.create;

import api.PlayerApi;
import api.player.models.Player;
import enums.Gender;
import enums.Role;
import helpers.players.PlayerCreationalHelpers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import player.BasePlayerTest;
import steps.player.PlayerSteps;
import utils.Utils;
import utils.player.PlayerUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreatePlayerPositiveTests extends BasePlayerTest {

    @Test(description = "Supervisor can create user with valid data", dataProvider = "playerValidData")
    public void supervisorCreatesUserTest(String creatorLogin, int age, Gender gender, Role role, String password) {
        var newPlayer = new Player(age,
                                   gender.getGender(),
                                   null,
                                   UUID.randomUUID().toString(),
                                   password,
                                   role.getRole(),
                                   UUID.randomUUID().toString());
        var createdPlayer = PlayerSteps.createPlayerAndValidateSuccessResponse(creatorLogin, newPlayer);
        toDeletePlayerIds.add(createdPlayer.id());
    }

    @DataProvider
    public Object[][] playerValidData() {
        return new Object[][]{
                {
                        SUPERVISOR_LOGIN,
                        //login of creator
                        17,
                        //boundary positive min pass length
                        Gender.MALE,
                        // positive gender choice
                        Role.USER,
                        // role of created user
                        Utils.getRandomStringOnlyWithLetters(7)
                        // password with only letters with boundary positive min length
                },
                {
                        ADMIN_LOGIN,
                        //role of creator
                        59,
                        //boundary positive max age
                        Gender.FEMALE,
                        // positive gender choice
                        Role.ADMIN,
                        // role of created user
                        Utils.getRandomStringOnlyWithNumbers(15)
                        // password with only nums with boundary positive max length
                },
        };
    }

    @Test(description = "Created admin (not already created admin with ADMIN login) can create users")
    public void createdAdminCanCreateAnotherUserTest() {
        var newPlayer = PlayerCreationalHelpers.createSuccessRandomAdminPlayer();
        var playerId = PlayerSteps.createPlayer(randomAdmin.login(), newPlayer).id();
        toDeletePlayerIds.add(playerId);
    }

    @Test(description = "Create player without password")
    public void createPlayerWithoutPasswordTest() {
        var randomPlayerData = Map.of("age",
                                      PlayerUtils.getRandomAge(),
                                      "gender",
                                      PlayerUtils.getRandomGender(),
                                      "login",
                                      UUID.randomUUID().toString(),
                                      "password",
                                      PlayerUtils.getRandomPassWithRandomLength(),
                                      "role",
                                      Role.USER.getRole(),
                                      "screenName",
                                      UUID.randomUUID().toString());

        var mapWithoutPassword = new HashMap<>(randomPlayerData);
        mapWithoutPassword.remove("password");

        PlayerApi.create(SUPERVISOR_LOGIN, mapWithoutPassword)
                 .statusCode(200)
                 .extract()
                 .response()
                 .as(Player.class);
    }

}
