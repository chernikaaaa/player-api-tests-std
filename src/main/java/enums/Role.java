package enums;

public enum Role {
    SUPERVISOR("supervisor"),
    ADMIN("admin"),
    USER("user");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
