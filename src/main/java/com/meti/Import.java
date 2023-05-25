package com.meti;

public class Import implements Node {
    public static final String Prefix = "import ";
    private final String value;

    public Import(String value) {
        this.value = value;
    }

    @Override
    public String render() {
        return Prefix + value;
    }
}
