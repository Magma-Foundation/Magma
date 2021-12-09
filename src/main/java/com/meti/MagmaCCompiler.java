package com.meti;

public class MagmaCCompiler {
    private final String input;

    public MagmaCCompiler(String input) {
        this.input = input;
    }

    String compile() {
        String output;
        if (input.startsWith("def ")) {
            var parameterStart = input.indexOf('(');
            var name = input.substring("def ".length(), parameterStart);
            output = "int " + name + "(){return 0;}";
        } else {
            output = "";
        }
        return output;
    }
}
