package magmac.app.lang.node;

/**
 * Distinguishes between namespace declarations and imports in the source.
 */

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
