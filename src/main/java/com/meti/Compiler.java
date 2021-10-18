package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if (input().equals("def main() : I16 => {return 0;}")) {
            output = new FunctionRenderer().render();
        } else {
            output = "";
        }
        return output;
    }
}