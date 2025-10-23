package tests.player.getall;

import org.testng.Assert;
import org.testng.annotations.Test;
import tests.base.BasePlayerTest;

public class GetAllPlayersTests extends BasePlayerTest {

    @Test(description = "Get all players returns list")
    public void getAll() {
        var resp = playerApi.getAll();
        Assert.assertEquals(resp.statusCode(), 200);
        int sz = resp.jsonPath().getList("players").size();
        Assert.assertTrue(sz >= 2, "should have at least predefined users");
    }

}
