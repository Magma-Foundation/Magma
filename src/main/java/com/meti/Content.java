package com.meti;

public class Content implements Node {
    private final String value;

    public Content(String value) {
        this.value = value;
    }

    @Override
    public String render() {
        return value;
    }
}
