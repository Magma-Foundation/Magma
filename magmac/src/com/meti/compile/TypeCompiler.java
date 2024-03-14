package com.meti.compile;

public record TypeCompiler(String type) {
    public String compile() {
        if (type.equals("void")) {
            return "Void";
        } else {
            var start = type().indexOf('<');
            var end = type().lastIndexOf('>');
            if (start != -1 && end != -1 && start < end) {
                var name = type().substring(0, start).strip();
                var subType = type().substring(start + 1, end).strip();
                return name + "[" + new TypeCompiler(subType).compile() + "]";
            } else {
                return type();
            }
        }
    }
}