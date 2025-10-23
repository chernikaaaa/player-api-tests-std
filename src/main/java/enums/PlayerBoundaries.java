package enums;

public enum PlayerBoundaries {
    MIN_AGE(17),
    MAX_AGE(59),
    MIN_PASS_LENGTH(7),
    MAX_PASS_LENGTH(15);

    private final Integer boundaryValue;

    PlayerBoundaries(Integer boundaryValue) {
        this.boundaryValue = boundaryValue;
    }

    public Integer getValue() {
        return boundaryValue;
    }
}
