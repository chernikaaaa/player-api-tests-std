package utils.player;

import api.player.models.Player;
import enums.Gender;
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
        var finalRandomGender = randomGender;
        while (Arrays.stream(Gender.values())
                     .anyMatch(genderValue -> genderValue.getGender().equalsIgnoreCase(finalRandomGender))) {
            randomGender = Utils.getRandomStringOnlyWithLetters(4);
        }
        return finalRandomGender;
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
        var minAge = 17;
        var maxAge = 59;
        return RANDOM.nextInt(maxAge - minAge + 1) + minAge;
    }

    public static String getRandomPassWithRandomLength() {
        var minPassLength = 7;
        var maxPassLength = 15; //TODO to some enum or something
        var randomPassLength = minPassLength + RANDOM.nextInt(maxPassLength - minPassLength + 1);
        return Utils.getRandomStringWithLettersAndNumbers(randomPassLength);
    }

}
