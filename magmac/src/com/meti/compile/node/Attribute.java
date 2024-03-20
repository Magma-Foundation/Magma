package com.meti.compile.node;

import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.java.JavaString;

import static com.meti.collect.option.None.None;

public interface Attribute {
    default Option<JavaList<? extends Node>> asListOfNodes() {
        return None();
    }

    default Option<Node> asNode() {
        return None();
    }

    default Option<JavaString> asString() {
        return None();
    }

    default Option<Integer> asInteger() {
        return None();
    }

    default Option<JavaList<JavaString>> asListOfStrings() {
        return None();
    }

    default boolean is(String type) {
        return false;
    }
}
