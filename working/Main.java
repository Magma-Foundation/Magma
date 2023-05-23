package com.meti;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        try {
            var source = Paths.get(".", "Main.java");
            var sources = Set.of(source);
            var application = new Application(sources);
            application.run();
        } catch (IOException e) {
            System.err.println("Failed to compile.");
            e.printStackTrace();
        }
    }
}
