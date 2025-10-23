package tests.player.update;

import org.testng.annotations.Test;
import tests.base.BasePlayerTest;

import java.util.Map;

public class UpdatePlayerTests extends BasePlayerTest {

    @Test(description = "Get then update screenName as supervisor")
    public void getThenUpdate() {
        var upd = playerApi.update("supervisor", id, Map.of("screenName", newSn));
    }

}
