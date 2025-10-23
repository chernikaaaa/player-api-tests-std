package utils;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Utils {

    private static final Random RANDOM = new Random();

    private Utils() {
        BaseUtility.getException();
    }

    public static String getRandomString(int length) {
        //TODO make check that at least one char and one num and one letter is present
        //TODO reuse alpha in other methods
        var allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+=-";
        return IntStream.range(0, length)
                        .mapToObj(i -> String.valueOf(allowedChars.charAt(RANDOM.nextInt(allowedChars.length()))))
                        .collect(Collectors.joining());
    }

    public static String getRandomStringOnlyWithLetters(int length) {
        var allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        return IntStream.range(0, length)
                        .mapToObj(i -> String.valueOf(allowedChars.charAt(RANDOM.nextInt(allowedChars.length()))))
                        .collect(Collectors.joining());
    }

    public static String getRandomStringWithLettersAndNumbers(int length) {
        //TODO make check that at least one num and one letter is present
        var allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return IntStream.range(0, length)
                        .mapToObj(i -> String.valueOf(allowedChars.charAt(RANDOM.nextInt(allowedChars.length()))))
                        .collect(Collectors.joining());
    }

}
