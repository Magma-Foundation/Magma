package com.meti;

public record Compiler(String input) {
    String compile() {
        if(input.startsWith("def ")) {
            var paramStart = input.indexOf('(');
            var name = input.substring("def ".length(), paramStart);
            return "int " + name + "(){return 0;}";
        }
        return "";
    }
}
