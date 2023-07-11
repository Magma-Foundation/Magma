package com.meti.app;

import com.meti.java.JavaList;
import com.meti.java.JavaMap;
import com.meti.java.JavaString;
import com.meti.java.List;

public record State(JavaMap<JavaString, List<JavaString>> imports) {
    public State() {
        this(new JavaMap<>());
    }

    State define(JavaString parent, JavaString child) {
        return new State(imports().insertOrMap(parent, list -> list.add(child), () -> JavaList.of(child)));
    }
}