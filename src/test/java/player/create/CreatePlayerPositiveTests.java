package player.create;

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

import java.util.UUID;

public class CreatePlayerPositiveTests extends BasePlayerTest {

    @Test(description = "Supervisor can create user with valid data", dataProvider = "playerValidData")
    public void supervisorCreatesUserTest(String creatorLogin,
                                          int age,
                                          int passLength,
                                          Gender gender,
                                          Role role,
                                          String password) {
        var newPlayer = new Player(age,
                                   gender.getGender(),
                                   null,
                                   UUID.randomUUID().toString(),
                                   password,
                                   role.getRole(),
                                   UUID.randomUUID().toString());
        PlayerSteps.createPlayerAndValidateSuccessResponse(creatorLogin, newPlayer);
    }

    @DataProvider
    public Object[][] playerValidData() {
        return new Object[][]{
                {
                        SUPERVISOR_LOGIN,
                        //login of creator
                        17,
                        //boundary positive min age
                        7,
                        //boundary positive min pass length
                        Gender.MALE,
                        // positive gender choice
                        Role.USER,
                        // role of created user
                        Utils.getRandomStringOnlyWithLetters(PlayerUtils.getRandomPassLength())
                        // password with only letters
                },
                {
                        ADMIN_LOGIN,
                        //role of creator
                        59,
                        //boundary positive max age
                        15,
                        //boundary positive max pass length
                        Gender.FEMALE,
                        // positive gender choice
                        Role.ADMIN,
                        // role of created user
                        Utils.getRandomStringOnlyWithNumbers(PlayerUtils.getRandomPassLength())
                        // password with only nums
                },
        };
    }

    @Test(description = "Created admin (not already created admin with ADMIN login) can create users")
    public void createdAdminCanCreateAnotherUser() {
        var newPlayer = PlayerCreationalHelpers.createSuccessRandomAdminPlayer();
        PlayerSteps.createPlayer(randomAdmin.login(), newPlayer);
    }

}
