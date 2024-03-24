package com.meti;

public record TypeCompiler(String type) {
    public String compile() {
        String outputType;
        if(type.equals("String")) {
            return "String";
        } else if (type.equals("void")) {
            outputType = "Void";
        } else if (type.equals("long")) {
            outputType = "I64";
        } else {
            outputType = "I32";
        }
        return outputType;
    }
}