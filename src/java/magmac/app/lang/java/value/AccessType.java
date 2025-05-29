package magmac.app.lang.java.value;

public enum AccessType {
    Data("data-access"),
    Method("method-access");

    private final String value;

    AccessType(String value) {
        this.value = value;
    }

    public String type() {
        return this.value;
    }
}
