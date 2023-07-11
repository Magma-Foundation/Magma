package com.meti.app;

import com.meti.java.JavaMap;
import com.meti.java.JavaSet;
import com.meti.java.JavaString;
import com.meti.java.NonEmptyList;

record Import(JavaMap<JavaString, JavaSet<Import>> children) {
    public Import() {
        this(new JavaMap<>());
    }

    public Import define(NonEmptyList<JavaString> segments) {
        return new Import(children.insert(segments.first(), JavaSet.empty()));
    }
}
