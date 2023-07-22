package com.meti.app;

import com.meti.java.String_;

public class Content implements Node {
    private final String_ value;

    public Content(String_ value) {
        this.value = value;
    }

    public static Node ofContent(String_ value) {
        return new Content(value);
    }

    @Override
    public String_ render() {
        return value;
    }
}
