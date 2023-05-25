package com.meti;

public final class JavaClass implements Node {
    public static final String PREFIX = "class ";
    private final boolean isPublic;
    private final String name;

    public JavaClass(String name) {
        this(name, false);
    }

    public JavaClass(String name, boolean isPublic) {
        this.name = name;
        this.isPublic = isPublic;
    }

    @Override
    public String render() {
        String publicString;
        if (isPublic) {
            publicString = "public";
        } else {
            publicString = "";
        }

        String keywordString;
        if (!publicString.isEmpty()) {
            keywordString = publicString + " ";
        } else {
            keywordString = "";
        }
        return keywordString + PREFIX + name + " {}";
    }

}