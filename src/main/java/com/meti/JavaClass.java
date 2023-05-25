package com.meti;

public final class JavaClass implements Node {
    public static final String ClassKeyword = "class ";
    private final boolean isPublic;
    private final String name;
    private final Node body;

    public JavaClass(String name, Block body) {
        this(name, false, body);
    }

    public JavaClass(String name, boolean isPublic, Node body) {
        this.name = name;
        this.isPublic = isPublic;
        this.body = body;
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
        return keywordString + ClassKeyword + name + " " + body.render();
    }

}