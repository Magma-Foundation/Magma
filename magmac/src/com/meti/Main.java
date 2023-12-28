package com.meti;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        var source = new DirectorySource(Paths.get(".", "magmac", "src"), "java");
        var generated = new Application(source, Paths.get(".", "magmac", "target")).run();
        System.out.printf("Generated '%d' targets.%n", generated.size());
    }
}
