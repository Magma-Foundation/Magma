package com.meti;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Application.compile("java", "mgs");
            Application.compile("mgs", "js");
            Application.compile("mgs", "d.ts");
            Application.compile("mgs", "c");
            Application.compile("mgs", "h");
        } catch (IOException | CompileException e) {
            throw new RuntimeException(e);
        }
    }
}
