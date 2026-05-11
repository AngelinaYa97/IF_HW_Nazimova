package constants;

public enum AuthUrn {
    REGISTER("/api/register"),
    LOGIN("/api/login"),
    LOGOUT("/api/logout");

    private final String value;

    AuthUrn(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}