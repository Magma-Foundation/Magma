package com.meti;

public record Compiler(String input) {
    String compile() {
        return input.isBlank() ? "" : "int main(){return 0;}";
    }
}
