package com.meti.app;

import com.meti.java.String_;

public class Content implements Renderable {
    private final String_ value;

    private Content(String_ value) {
        this.value = value;
    }

    public static Renderable ofContent(String_ value) {
        return new Content(value);
    }

    @Override
    public String_ render() {
        return value;
    }
}
