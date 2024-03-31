package com.meti;

public record MagmaDefinitionHeader(String flagString, String mutabilityString, String name, String type) {
    String render() {
        return "\n\t" + flagString() + mutabilityString() + name() + " : " + type();
    }
}