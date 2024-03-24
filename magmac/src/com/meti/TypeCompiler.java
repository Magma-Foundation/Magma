package com.meti;

public record TypeCompiler(String type) {
    String compile() {
        String outputType;
        if (type.equals("void")) {
            outputType = "Void";
        } else if (type.equals("long")) {
            outputType = "I64";
        } else {
            outputType = "I32";
        }
        return outputType;
    }
}