package com.meti;

public record MethodNode(String name, String parameters, String body) {
    String renderMethod() {
        return "export class def " + name() + parameters() + " => " + body();
    }
}