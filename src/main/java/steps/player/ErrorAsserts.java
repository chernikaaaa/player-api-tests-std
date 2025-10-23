package steps.player;

import org.testng.Assert;
import utils.BaseUtility;

public class ErrorAsserts {

    private ErrorAsserts() {
        BaseUtility.getException();
    }

    public static void assertErroMessage(String actualMessage, String expectedMessage) {
        Assert.assertNotNull(actualMessage, "Error message should be present in response");
        Assert.assertEquals(actualMessage, expectedMessage, "Error message should = " + expectedMessage);
    }

}
