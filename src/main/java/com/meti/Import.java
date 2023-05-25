package com.meti;

public class Import {
    public static final String Prefix = "import ";
    private final String value;

    public Import(String value) {
        this.value = value;
    }

    String render() {
        return Prefix + value;
    }
}
