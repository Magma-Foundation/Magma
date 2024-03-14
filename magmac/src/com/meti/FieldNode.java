package com.meti;

import java.util.List;

import static com.meti.Some.Some;

public record FieldNode(int indent, List<String> flags, String name, String value) {
    Option<String> renderField() {
        var flagsString = String.join(" ", flags());
        var withSuffix = flagsString.isEmpty() ? "" : flagsString + " ";
        return Some("\t".repeat(indent()) + withSuffix + name() + " = " + value() + ";\n");
    }
}