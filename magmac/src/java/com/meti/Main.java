package com.meti;

import java.io.IOException;

import static com.meti.Compiler.compile;

public class Main {
    public static void main(String[] args) {
        try {
            compile("java", "mgs");
            compile("mgs", "js");
            compile("mgs", "d.ts");
            compile("mgs", "c");
            compile("mgs", "h");
        } catch (IOException | CompileException e) {
            e.printStackTrace();
        }
    }
}