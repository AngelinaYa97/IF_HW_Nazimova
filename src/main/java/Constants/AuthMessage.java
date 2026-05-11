package constants;

public enum AuthMessage {
    SUCCESS_REGISTER("success register"),
    SUCCESS_LOGOUT("success logout"),
    NOT_FOUND("not found"),
    NOT_RIGHT_PASS("not right pass");

    private final String value;

    AuthMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}