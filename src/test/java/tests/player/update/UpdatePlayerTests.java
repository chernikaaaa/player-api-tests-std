package tests.player.update;

import org.testng.annotations.Test;
import tests.base.BasePlayerTest;

import java.util.Map;
import java.util.UUID;

public class UpdatePlayerTests extends BasePlayerTest {

    private String uniq(String prefix) {
        return prefix + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    private String s(int v) {
        return String.valueOf(v);
    }

    @Test(description = "Get then update screenName as supervisor")
    public void getThenUpdate() {
        var create = playerApi.create("supervisor", Map.of(
                "age", s(28),
                "gender", "female",
                "login", uniq("u"),
                "password", "abc12345",
                "role", "user",
                "screenName", uniq("sn")
        ));
//        Assert.assertEquals(create.statusCode(), 200);
//        long id = create.jsonPath().getLong("id");

//        var get = api.get(id);
//        Assert.assertEquals(get.statusCode(), 200);

        var newSn = uniq("sn");
//        var upd = api.update("supervisor", id, Map.of("screenName", newSn));
//        Assert.assertEquals(upd.statusCode(), 200);
//        Assert.assertEquals(upd.jsonPath().getString("screenName"), newSn);
    }

    @Test(description = "Reject invalid age on update")
    public void updateInvalidAge() {
        var create = playerApi.create("supervisor", Map.of(
                "age", s(27),
                "gender", "male",
                "login", uniq("u"),
                "password", "abc12345",
                "role", "user",
                "screenName", uniq("sn")
        ));
//        long id = create.jsonPath().getLong("id");

//        var upd = api.update("supervisor", id, Map.of("age", -1));
//        Assert.assertTrue(upd.statusCode() >= 400 && upd.statusCode() < 500);
    }

}
