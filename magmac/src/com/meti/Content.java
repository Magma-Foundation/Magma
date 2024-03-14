package com.meti;

public class Content implements Node {
    private final String value;

    public Content(String value, int indent) {
        this.value = value;
    }

    @Override
    public Option<String> render() {
        return Some.Some(value);
    }
}
