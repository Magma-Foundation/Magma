package com.meti;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Compiler.compile("java", "mgs");
            Compiler.compile("mgs", "js");
            Compiler.compile("mgs", "d.ts");
            Compiler.compile("mgs", "c");
            Compiler.compile("mgs", "h");
        } catch (IOException | CompileException e) {
            e.printStackTrace();
        }
    }
}