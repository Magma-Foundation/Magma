package magmac.app.lang.node;

public enum JavaAccessType {
    Data("data-access"),
    Method("method-access");

    private final String value;

    JavaAccessType(String value) {
        this.value = value;
    }

    public String type() {
        return this.value;
    }
}
