package steps.player;

import api.player.models.AllPlayersResponse;
import api.player.models.Player;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class PlayerAsserts {

    private PlayerAsserts() {
        throw new IllegalStateException("Utility class");
    }

    public static void assertGetAllPlayersResponse(List<AllPlayersResponse.AllPlayerResponseItem> allPlayers,
                                                   List<Integer> expectedPlayerIds) {
        assertExpectedPlayersFoundInList(allPlayers, expectedPlayerIds);
        assertThereAreNotFullDuplicatesInPlayers(allPlayers);
        assertThereAreNotDuplicatedIdsInPlayers(allPlayers);
        assertThereAreNotDuplicatedScreenNameInPlayers(allPlayers);
    }

    @Step("Assert ids equals")
    public static void assertIdsEquals(Player actualPlayer, Integer expectedId) {
        Assert.assertNotNull(actualPlayer.id(), "ID should not be null");
        Assert.assertEquals(actualPlayer.id(), expectedId, "ID should be equals to updated one");
    }

    public static void assertPlayer(Player actualPlayer, Player expectedPlayer) {
        var age = expectedPlayer.age();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(actualPlayer.age(), age, "Age mismatch"); //TODO change msgs
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

    private static void assertThereAreNotDuplicatedScreenNameInPlayers(List<AllPlayersResponse.AllPlayerResponseItem> allPlayers) {
        var hasDuplicateScreenNames = allPlayers.stream()
                                                .map(player -> player.screenName())
                                                .distinct()
                                                .count() != allPlayers.size();
        Assert.assertFalse(hasDuplicateScreenNames, "Duplicate player screenName found");
    }

    private static void assertThereAreNotDuplicatedIdsInPlayers(List<AllPlayersResponse.AllPlayerResponseItem> allPlayers) {
        var hasDuplicateIds = allPlayers.stream()
                                        .map(player -> player.id())
                                        .distinct()
                                        .count() != allPlayers.size();
        Assert.assertFalse(hasDuplicateIds, "Duplicate player IDs found");
    }

    private static void assertThereAreNotFullDuplicatesInPlayers(List<AllPlayersResponse.AllPlayerResponseItem> allPlayers) {
        var disctinctedCount = allPlayers.stream().distinct().count();
        Assert.assertEquals(allPlayers.size(),
                            disctinctedCount,
                            "There should not be duplicated players in the get all response");
    }

    private static void assertExpectedPlayersFoundInList(List<AllPlayersResponse.AllPlayerResponseItem> allPlayers,
                                                         List<Integer> expectedPlayerIds) {
        var playerIds = allPlayers.stream().map(player -> player.id()).toList();
        Assert.assertTrue(playerIds.containsAll(expectedPlayerIds),
                          "Player ids from get all list should contain created players");
    }

}
