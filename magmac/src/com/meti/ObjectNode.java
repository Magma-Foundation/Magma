package com.meti;

import java.util.List;

import static com.meti.Some.Some;

public record ObjectNode(List<String> flags, String name, String value) {
    Option<String> renderObject() {
        var flagsString = flags().isEmpty() ? String.join(" ", flags()) + " " : "";
        return Some(flagsString + "object " + name() + " = " + value());
    }
}