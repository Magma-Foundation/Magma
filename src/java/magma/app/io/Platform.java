package magma.app.io;

public enum Platform {
    TypeScript("node", "ts"),
    Magma("magma", "mgs"),
    Windows("windows", "h", "c");

    public final String root;
    public final String[] extension;

    Platform(final String root, final String... extensions) {
        this.root = root;
        this.extension = extensions;
    }
}
