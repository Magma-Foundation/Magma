package com.meti;

public record ObjectNode(String flagString, String name, String content) {
    String renderObject() {
        return flagString() + "object " + name() + " = {" + content() + "\n}";
    }
}