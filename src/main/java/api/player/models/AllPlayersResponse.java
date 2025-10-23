package api.player.models;

import java.util.List;

public record AllPlayersResponse(List<AllPlayerResponseItem> players) {

    public record AllPlayerResponseItem(Integer age,
                                        String gender,
                                        Integer id,
                                        String role,
                                        String screenName) {

    }

}
