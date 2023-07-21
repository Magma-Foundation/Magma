package com.meti.app;

import com.meti.java.String_;

public class Content {
    private final String_ value;

    public Content(String_ value) {
        this.value = value;
    }

    public String_ unwrap() {
        return value;
    }
}
