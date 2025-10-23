package steps.player;

import api.player.models.AllPlayersResponse;
import api.player.models.Player;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import utils.BaseUtility;

import java.util.List;

public class PlayerAsserts {

    private PlayerAsserts() {
        BaseUtility.getException();
    }

    public static void assertGetAllPlayersResponse(List<AllPlayersResponse.AllPlayerResponseItem> allPlayers,
                                                   List<Integer> expectedPlayerIds) {
        assertExpectedPlayersFoundInList(allPlayers, expectedPlayerIds);
        assertThereAreNotFullDuplicatesInPlayers(allPlayers);
        assertThereAreNotDuplicatedIdsInPlayers(allPlayers);
        assertThereAreNotDuplicatedScreenNameInPlayers(allPlayers);
    }

    public static void assertIdsEquals(Player actualPlayer, Integer expectedId) {
        Assert.assertNotNull(actualPlayer.id(), "Player id should not be null");
        Assert.assertEquals(actualPlayer.id(), expectedId, "Player id should be as expected");
    }

    public static void assertPlayerDetails(Player actualPlayer, Player expectedPlayer) {
        var age = expectedPlayer.age();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(actualPlayer.age(), age, "Player age should be as in expected player");
        softAssert.assertEquals(actualPlayer.gender(),
                                expectedPlayer.gender(),
                                "Player gender should be as in expected player");
        softAssert.assertEquals(actualPlayer.login(),
                                expectedPlayer.login(),
                                "Player login should be as in expected player");
        softAssert.assertEquals(actualPlayer.role(),
                                expectedPlayer.role(),
                                "Player role should be as in expected player");
        softAssert.assertEquals(actualPlayer.screenName(),
                                expectedPlayer.screenName(),
                                "Player screenName should be as in expected player");
//        softAssert.assertNull(actualPlayer.password(),
//                              "Player password should be as in expected player");
// for security reasons return password in reponse is not ok
        softAssert.assertAll();
    }

    public static void assertPlayerId(Player actualPlayer) {
        Assert.assertNotNull(actualPlayer.id(), "Player id should not be null");
        Assert.assertTrue(actualPlayer.id() > 0, "Player id should be greater than 0");
    }

    private static void assertThereAreNotDuplicatedScreenNameInPlayers(List<AllPlayersResponse.AllPlayerResponseItem> allPlayers) {
        var hasDuplicateScreenNames = allPlayers.stream()
                                                .map(AllPlayersResponse.AllPlayerResponseItem::screenName)
                                                .distinct()
                                                .count() != allPlayers.size();
        Assert.assertFalse(hasDuplicateScreenNames, "Duplicate player screen names should not be found");
    }

    private static void assertThereAreNotDuplicatedIdsInPlayers(List<AllPlayersResponse.AllPlayerResponseItem> allPlayers) {
        var hasDuplicateIds = allPlayers.stream()
                                        .map(AllPlayersResponse.AllPlayerResponseItem::id)
                                        .distinct()
                                        .count() != allPlayers.size();
        Assert.assertFalse(hasDuplicateIds, "Duplicate player ids should not be found");
    }

    private static void assertThereAreNotFullDuplicatesInPlayers(List<AllPlayersResponse.AllPlayerResponseItem> allPlayers) {
        var countWithoutDuplicates = allPlayers.stream().distinct().count();
        Assert.assertEquals(allPlayers.size(),
                            countWithoutDuplicates,
                            "Duplicate full player records should not be found");
    }

    private static void assertExpectedPlayersFoundInList(List<AllPlayersResponse.AllPlayerResponseItem> allPlayers,
                                                         List<Integer> expectedPlayerIds) {
        var playerIds = allPlayers.stream().map(AllPlayersResponse.AllPlayerResponseItem::id).toList();
        Assert.assertTrue(playerIds.containsAll(expectedPlayerIds),
                          "Player ids from get all list should contain created players");
    }

}
