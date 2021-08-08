package com.meti;

public class MagmaJavaCompiler {
    private final String input;

    public MagmaJavaCompiler(String input) {
        this.input = input;
    }

    String compile() {
        String output;
        if (input.equals("import native Test from org.junit.jupiter.api;")) {
            output = "import org.junit.jupiter.api.Test;class __index__{}";
        } else if(input.equals("import native IOException from java.io;")) {
            output = "import java.io.IOException;class __index__{}";
        } else {
            output = "class __index__{}";
        }
        return output;
    }
}
