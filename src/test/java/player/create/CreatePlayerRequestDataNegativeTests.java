package player.create;

import helpers.players.PlayerCreationalHelpers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import player.BasePlayerTest;
import steps.player.PlayerSteps;
import utils.Utils;
import utils.player.PlayerUtils;

public class CreatePlayerRequestDataNegativeTests extends BasePlayerTest {

//    @Test(description = "Data required in request test", dataProvider = "notValidData")
//    public void dataRequiredInRequestTest(Integer age,
//                                          String gender,
//                                          String login,
//                                          String password,
//                                          String role,
//                                          String screenName,
//                                          String expectedMessage) {
//        Map<String, ? extends Serializable> playerParams = Map.of("age",
//                                                                  age,
//                                                                  "gender",
//                                                                  gender,
//                                                                  "login",
//                                                                  login,
//                                                                  "password",
//                                                                  password,
//                                                                  "role",
//                                                                  role,
//                                                                  "screenName",
//                                                                  screenName);
//        var response = PlayerApi.create(SUPERVISOR_LOGIN, playerParams).statusCode(400).extract().response();
//        var message = response.jsonPath().getString("message");
//
//        ErrorAsserts.assertErroMessage(message, expectedMessage);
//    }
//
//    @DataProvider
//    public Object[][] notValidData() {
//        return new Object[][]{
//                {
//                        null,
//                        Gender.FEMALE.getGender(),
//                        UUID.randomUUID().toString(),
//                        PlayerUtils.getRandomPassWithRandomLength(),
//                        Role.USER.getRole(),
//                        UUID.randomUUID().toString(),
//                        "some message about age validation"
//                },
//                {
//                        Utils.getRandomString(2),
//                        Gender.FEMALE.getGender(),
//                        UUID.randomUUID().toString(),
//                        PlayerUtils.getRandomPassWithRandomLength(),
//                        Role.USER.getRole(),
//                        UUID.randomUUID().toString(),
//                        "some message about age validation"
//                },
//                {
//                        PlayerUtils.getRandomAge(),
//                        null,
//                        UUID.randomUUID().toString(),
//                        PlayerUtils.getRandomPassWithRandomLength(),
//                        Role.USER.getRole(),
//                        UUID.randomUUID().toString(),
//                        "some message about gender validation"
//                },
//                {
//                        PlayerUtils.getRandomAge(),
//                        Gender.FEMALE.getGender(),
//                        null,
//                        PlayerUtils.getRandomPassWithRandomLength(),
//                        Role.USER.getRole(),
//                        UUID.randomUUID().toString(),
//                        "some message about login validation"
//                },
//                {
//                        PlayerUtils.getRandomAge(),
//                        Gender.FEMALE.getGender(),
//                        UUID.randomUUID().toString(),
//                        null,
//                        Role.USER.getRole(),
//                        UUID.randomUUID().toString(),
//                        "some message about pass validation"
//                },
//                {
//                        PlayerUtils.getRandomAge(),
//                        Gender.FEMALE.getGender(),
//                        UUID.randomUUID().toString(),
//                        PlayerUtils.getRandomPassWithRandomLength(),
//                        null,
//                        UUID.randomUUID().toString(),
//                        "some message about role validation"
//                },
//                {
//                        PlayerUtils.getRandomAge(),
//                        Gender.FEMALE.getGender(),
//                        UUID.randomUUID().toString(),
//                        PlayerUtils.getRandomPassWithRandomLength(),
//                        Role.USER.getRole(),
//                        null,
//                        "some message about screenname validation"
//                },
//        };
//    }

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
        //TODO when all of these tests will work we can move newPlayer template to  @BeforeClass because it isn't created in these tests
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

}
