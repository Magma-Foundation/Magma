package com.meti.app;

import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public record Class_(String_ name, Renderable compiledBody) implements Renderable {
    @Override
    public String_ render() {
        return fromSlice("class def " + name().unwrap() + "() => " + compiledBody().render().unwrap());
    }
}