package com.meti.magma;

public record ObjectNode(String flagString, String name, String content) {
    public String render() {
        return flagString() + "object " + name() + " = {" + content() + "\n}";
    }
}