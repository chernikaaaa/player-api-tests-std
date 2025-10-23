package Endpoints;

import utils.BaseUtility;

public final class Endpoints {

    private Endpoints() {
        BaseUtility.getException();
    }

    public static final String CREATE_PLAYER = "/player/create/{editor}";
    public static final String DELETE_PLAYER = "/player/delete/{editor}";
    public static final String GET_PLAYER = "/player/get";
    public static final String GET_ALL_PLAYERS = "/player/update/{editor}/{id}";

}
