package com.meti.app;

import com.meti.java.String_;

public class Content implements Renderable {
    private final String_ value;

    public Content(String_ value) {
        this.value = value;
    }

    @Override
    public String_ render() {
        return value;
    }
}
