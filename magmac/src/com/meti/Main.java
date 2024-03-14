package com.meti;

import com.meti.compile.Application;
import com.meti.compile.CompileException;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var source = Paths.get(".", "Personal", "Magma", "magmac", "test", "com", "meti", "ApplicationTest.java");
        try {
            new Application(source).run();
        } catch (IOException | CompileException e) {
            throw new RuntimeException(e);
        }
    }
}
