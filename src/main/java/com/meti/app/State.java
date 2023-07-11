package com.meti.app;

import com.meti.java.JavaString;
import com.meti.java.NonEmptyList;

public record State(Import root) {
    public State() {
        this(new Import());
    }

    State define(NonEmptyList<JavaString> segments) {
        return new State(root.define(segments));
    }

}