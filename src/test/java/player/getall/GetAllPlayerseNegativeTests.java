package player.getall;

import org.testng.annotations.Test;
import player.BasePlayerTest;

public class GetAllPlayerseNegativeTests extends BasePlayerTest {

    @Test(description = "Unauthorized user cannot get all  users")
    public void unauthorizedCannotGetAllUserTest() {
        //TODO cannot be deployed due to lack of unauthenticated access in api
    }

    @Test(description = "Forbidden user cannot get all  users")
    public void forbiddenCannotGetAllUserTes() {
        //TODO cannot be deployed due to lack of user mention in request
    }

    @Test(description = "Get all players failed with 404 error")
    public void getAllFailedDueToBadDataTest() {
        //TODO cannot be deployed due to lack of data in request
    }

}
