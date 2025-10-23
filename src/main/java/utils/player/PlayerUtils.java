package utils.player;

import api.player.models.Player;
import enums.Gender;
import enums.PlayerBoundaries;
import utils.BaseUtility;
import utils.Utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public final class PlayerUtils {

    private static final Random RANDOM = new Random();

    private PlayerUtils() {
        BaseUtility.getException();
    }

    public static String getRandomIncorrectGender() {
        var randomGender = Utils.getRandomStringOnlyWithLetters(4);
        while (Arrays.stream(Gender.values()).map(Gender::getGender).toList().contains(randomGender)) {
            randomGender = Utils.getRandomStringOnlyWithLetters(4);
        }
        return randomGender;
    }

    public static Map<String, Serializable> buildMapParamsFromPlayerObject(Player player) {
        return Map.of("age",
                      player.age(),
                      "gender",
                      player.gender(),
                      "login",
                      player.login(),
                      "password",
                      player.password(),
                      "role",
                      player.role(),
                      "screenName",
                      player.screenName());
    }

    public static String getRandomGender() {
        return Gender.values()[RANDOM.nextInt(Gender.values().length)].getGender();
    }

    public static int getRandomAge() {
        return RANDOM.nextInt(
                PlayerBoundaries.MAX_AGE.getValue() - PlayerBoundaries.MIN_AGE.getValue() + 1)
                + PlayerBoundaries.MIN_AGE.getValue();
    }

    public static String getRandomPassWithRandomLength() {
        var randomPassLength = PlayerBoundaries.MIN_PASS_LENGTH.getValue() + RANDOM.nextInt(
                PlayerBoundaries.MAX_PASS_LENGTH.getValue()
                        - PlayerBoundaries.MIN_PASS_LENGTH.getValue() + 1);
        return Utils.getRandomStringWithLettersAndNumbers(randomPassLength);
    }

}
