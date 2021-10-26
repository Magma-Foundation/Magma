package com.meti.compile.attribute;

import com.meti.compile.Input;
import com.meti.compile.Node;

import java.util.stream.Stream;

public interface Attribute {
    default Input asInput() {
        throw new UnsupportedOperationException();
    }

    default int asInteger() {
        throw new UnsupportedOperationException();
    }

    default Node asNode() {
        throw new UnsupportedOperationException();
    }

    default Stream<Node> asNodeStream() {
        throw new UnsupportedOperationException();
    }

    default String asString() {
        throw new UnsupportedOperationException();
    }

    enum Group {
        Node
    }

    enum Type {
        Children, Value
    }
}
