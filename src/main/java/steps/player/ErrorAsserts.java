package steps.player;

import org.testng.Assert;

public class ErrorAsserts {

    private ErrorAsserts() {
        throw new IllegalStateException("Utility class");
    }

    public static void assertErroMessage(String actualMessage, String expectedMessage) {
        Assert.assertNotNull(actualMessage, "Error message should be present in response");
        Assert.assertEquals(actualMessage, expectedMessage, "Error message should = " + expectedMessage);
    }

}
