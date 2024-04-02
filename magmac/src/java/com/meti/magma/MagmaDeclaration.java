package com.meti.magma;

public record MagmaDeclaration(String flagString, String mutabilityString, String name, String type) {
    public String render() {
        return flagString() + mutabilityString() + name() + " : " + type();
    }
}