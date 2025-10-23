package player.create;

import api.player.PlayerApi;
import enums.Role;
import helpers.players.PlayerCreationalHelpers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import player.BasePlayerTest;
import steps.player.ErrorAsserts;
import steps.player.PlayerSteps;
import utils.Utils;
import utils.player.PlayerUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreatePlayerRequestDataNegativeTests extends BasePlayerTest {

    @Test(description = "Supervisor can't create player with invalid age", dataProvider = "notValidAges")
    public void supervisorCanNotCreatePlayerWithInvalidAge(int notValidAge) {
        var newPlayer = PlayerCreationalHelpers.createSuccessRandomAdminPlayer();
        newPlayer = PlayerCreationalHelpers.withAge(newPlayer, notValidAge);
        PlayerSteps.createPlayerAndValidateBadRequestMessage(SUPERVISOR_LOGIN,
                                                             newPlayer,
                                                             "some message about age validation");
    }

    @DataProvider
    public Object[][] notValidAges() {
        return new Object[][]{
                {
                        16,
                        //boundary negative age
                },
                {
                        60,
                        //boundary negative age
                }
        };
    }

    @Test(description = "Player with same login can not be created")
    public void playerWithSameLoginCanNotBeCreated() {
        var newPlayer = PlayerCreationalHelpers.createSuccessRandomAdminPlayer();
        newPlayer = PlayerCreationalHelpers.withLogin(newPlayer, ADMIN_LOGIN);
        PlayerSteps.createPlayerAndValidateBadRequestMessage(ADMIN_LOGIN,
                                                             newPlayer,
                                                             "some message about login validation");
    }

    @Test(description = "Player with same screenname can not be created")
    public void playerWithSameScreenNameCanNotBeCreated() {
        var newPlayer = PlayerCreationalHelpers.createSuccessRandomAdminPlayer();
        newPlayer = PlayerCreationalHelpers.withScreenName(newPlayer, randomUser.screenName());
        PlayerSteps.createPlayerAndValidateBadRequestMessage(ADMIN_LOGIN,
                                                             newPlayer,
                                                             "some message about login validation");
    }

    @Test(description = "Player with incorrect password length can not be created", dataProvider = "notValidPasswordLength")
    public void playerWithIncorrectPasswordLengthCanNotBeCreated(int notValidPasswordLength) {
        var newPlayer = PlayerCreationalHelpers.createSuccessRandomAdminPlayer();
        //TODO when all of these tests will work we can move newPlayer template to @BeforeClass because it isn't created in these tests
        // so we can reuse it and reduce code duplication
        newPlayer = PlayerCreationalHelpers.withPassword(newPlayer,
                                                         Utils.getRandomStringWithLettersAndNumbers(
                                                                 notValidPasswordLength));
        PlayerSteps.createPlayerAndValidateBadRequestMessage(ADMIN_LOGIN,
                                                             newPlayer,
                                                             "some message about password length validation");
    }

    @Test(description = "Player with incorrect password can not be created")
    public void playerWithIncorrectPasswordCanNotBeCreated() {
        var newPlayer = PlayerCreationalHelpers.createSuccessRandomAdminPlayer();
        newPlayer = PlayerCreationalHelpers.withPassword(newPlayer, Utils.getRandomString(12));
        PlayerSteps.createPlayerAndValidateBadRequestMessage(ADMIN_LOGIN,
                                                             newPlayer,
                                                             "some message about password chars validation");
    }

    @Test(description = "Player with incorrect gender can not be created")
    public void playerWithIncorrectGenderCanNotBeCreated() {
        var newPlayer = PlayerCreationalHelpers.createSuccessRandomAdminPlayer();
        newPlayer = PlayerCreationalHelpers.withGender(newPlayer, PlayerUtils.getRandomIncorrectGender());
        PlayerSteps.createPlayerAndValidateBadRequestMessage(ADMIN_LOGIN,
                                                             newPlayer,
                                                             "some message about password length validation");
    }

    @DataProvider
    public Object[][] notValidPasswordLength() {
        return new Object[][]{
                {
                        6,
                        //boundary negative age
                },
                {
                        16,
                        //boundary negative age
                },
        };
    }

    @Test(description = "Data required in request test", dataProvider = "notValidData")
    public void dataRequiredInRequestTest(Map<String, ? extends Serializable> data) {
        var response = PlayerApi.create(SUPERVISOR_LOGIN, data).statusCode(404).extract().response();
        var message = response.jsonPath().getString("message");
        ErrorAsserts.assertErroMessage(message, "expected message");
    }

    @DataProvider
    public Object[][] notValidData() {
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

        var mapWithoutAge = new HashMap<>(randomPlayerData);
        mapWithoutAge.remove("age");

        var mapWitStringAge = new HashMap<>(randomPlayerData);
        mapWithoutAge.put("age", Utils.getRandomString(3));

        var mapWithoutGender = new HashMap<>(randomPlayerData);
        mapWithoutAge.remove("gender");

        var mapWithoutLogin = new HashMap<>(randomPlayerData);
        mapWithoutAge.remove("login");

        var mapWithIntLogin = new HashMap<>(randomPlayerData);
        mapWithoutAge.put("login", Utils.getRandomInt());

        var mapWithoutPass = new HashMap<>(randomPlayerData);
        mapWithoutAge.remove("pass");

        var mapWithoutRole = new HashMap<>(randomPlayerData);
        mapWithoutAge.remove("role");

        var mapWithoutScreenName = new HashMap<>(randomPlayerData);
        mapWithoutAge.remove("screenName");

        var mapWithNumScreenName = new HashMap<>(randomPlayerData);
        mapWithoutAge.put("screenName", Utils.getRandomInt());

        return new Object[][]{
                {mapWithoutAge},
                {mapWitStringAge},
                {mapWithoutGender},
                {mapWithoutRole},
                {mapWithoutLogin},
                {mapWithoutPass},
                {mapWithoutScreenName},
                {mapWithNumScreenName},
                {mapWithIntLogin}
        };
    }

}
