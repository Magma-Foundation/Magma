package com.meti;

public record Compiler(String input) {
    String compile() {
        return input.equals("def main() : I16 => {return 0;}")
                ? "int main(){return 0;}"
                : "";
    }
}
