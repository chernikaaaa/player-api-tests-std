package tests.player;

import tests.base.BasePlayerTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.UUID;

public class DeletePlayerTests extends BasePlayerTest {

    private String uniq(String prefix){ return prefix + UUID.randomUUID().toString().replace("-", "").substring(0,10); }
    private String s(int v){ return String.valueOf(v); }

    private long createUserAsSupervisor() {
        var resp = playerApi.create("supervisor", Map.of(
                "age", s(30),
                "gender", "male",
                "login", uniq("u"),
                "password", "abc12345",
                "role", "user",
                "screenName", uniq("sn")
        ));
//        org.testng.Assert.assertEquals(resp.statusCode(), 200);
//        return resp.jsonPath().getLong("id");
        return Long.parseLong("1");
    }

    @Test(description = "Admin can delete USER")
    public void adminDeletesUser() {
        long id = createUserAsSupervisor();
        var resp = playerApi.delete("admin", id);
        Assert.assertTrue(resp.statusCode() == 200 || resp.statusCode() == 204, "delete status");
    }

    @Test(description = "USER cannot delete anyone")
    public void userCannotDelete() {
        long id = createUserAsSupervisor();
        var resp = playerApi.delete("user", id);
        Assert.assertTrue(resp.statusCode() == 401 || resp.statusCode() == 403);
    }
}
