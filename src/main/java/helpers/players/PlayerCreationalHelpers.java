
package helpers.players;

import api.player.models.Player;
import enums.Role;
import io.qameta.allure.Step;
import utils.player.PlayerUtils;

import java.util.UUID;

public class PlayerCreationalHelpers {

    private PlayerCreationalHelpers() {
        throw new IllegalStateException("Utility class"); //TODO make baseutility and move it to it and reuse anywhere
    }

    @Step("Create success random admin player")
    public static Player createSuccessRandomAdminPlayer() {
        return createSuccessRandomPlayer(Role.ADMIN);
    }

    @Step("Create success random player with role {role}")
    public static Player createSuccessRandomPlayer(Role role) {
        return new Player(PlayerUtils.getRandomAge(),
                          PlayerUtils.getRandomGender(),
                          null,
                          UUID.randomUUID().toString(),
                          PlayerUtils.getRandomPassWithRandomLength(),
                          role.getRole(),
                          UUID.randomUUID().toString());
    }

    public static Player withAge(Player player, Integer age) {
        return new Player(age, //TODO change to use creation random player
                          player.gender(),
                          player.id(),
                          player.login(),
                          player.password(),
                          player.role(),
                          player.screenName());
    }

    public static Player withId(Player player, Integer id) {
        return new Player(player.age(),
                          player.gender(),
                          id,
                          player.login(),
                          player.password(),
                          player.role(),
                          player.screenName());
    }

    public static Player withGender(Player player, String gender) {
        return new Player(player.age(),
                          gender,
                          player.id(),
                          player.login(),
                          player.password(),
                          player.role(),
                          player.screenName());
    }

    public static Player withLogin(Player player, String login) {
        return new Player(player.age(),
                          player.gender(),
                          player.id(),
                          login,
                          player.password(),
                          player.role(),
                          player.screenName());
    }

    public static Player withPassword(Player player, String password) {
        return new Player(player.age(),
                          player.gender(),
                          player.id(),
                          player.login(),
                          password,
                          player.role(),
                          player.screenName());
    }

    public Player withRole(Player player, String role) {
        return new Player(player.age(),
                          player.gender(),
                          player.id(),
                          player.login(),
                          player.password(),
                          role,
                          player.screenName());
    }

    public static Player withScreenName(Player player, String screenName) {
        return new Player(player.age(),
                          player.gender(),
                          player.id(),
                          player.login(),
                          player.password(),
                          player.role(),
                          screenName);
    }

}
