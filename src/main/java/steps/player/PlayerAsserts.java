package steps.player;

import api.player.models.Player;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class PlayerAsserts {

    private PlayerAsserts() {
        throw new IllegalStateException("Utility class");
    }

    public static void assertPlayer(Player actualPlayer, Player expectedPlayer) {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(actualPlayer.age(), expectedPlayer.age(), "Age mismatch"); //TODO change msgs
        softAssert.assertEquals(actualPlayer.gender(), expectedPlayer.gender(), "Gender mismatch");
        softAssert.assertEquals(actualPlayer.login(), expectedPlayer.login(), "Login mismatch");
        softAssert.assertEquals(actualPlayer.role(), expectedPlayer.role(), "Role mismatch");
        softAssert.assertEquals(actualPlayer.screenName(), expectedPlayer.screenName(), "ScreenName mismatch");
//        softAssert.assertNull(actualPlayer.password(),
//                              "Password should be null"); //for security reasons return password in reponse is not ok
        softAssert.assertAll();
    }

    public static void assertPlayerId(Player actualPlayer) {
        Assert.assertNotNull(actualPlayer.id(), "ID should not be null");
        Assert.assertTrue(actualPlayer.id() > 0, "ID should be positive");
    }

}
