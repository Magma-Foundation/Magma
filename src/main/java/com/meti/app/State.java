package com.meti.app;

import com.meti.java.JavaString;
import com.meti.java.NonEmptyList;
import com.meti.java.String_;

public record State(Import root) {
    public State() {
        this(new Import(JavaString.empty()));
    }

    State define(NonEmptyList<String_> segments) {
        return new State(root.addPath(segments));
    }
}