package magma.app;

public enum Platform {
    TypeScript("node", "ts"),
    Magma("magma", "mgs"),
    Windows("windows", "h", "c");

    final String root;
    final String[] extension;

    Platform(final String root, final String... extensions) {
        this.root = root;
        this.extension = extensions;
    }
}
