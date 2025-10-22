package api.player.models;

public record Player(
        Integer age,
        String gender,
        Integer id,
        String login,
        String password,
        String role,
        String screenName
) {

}
