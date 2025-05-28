package magmac.app.lang.java;

public enum NamespacedType {
    Package("package"),
    Import("import");

    private final String type;

    NamespacedType(String type) {
        this.type = type;
    }

    public String type() {
        return this.type;
    }
}
