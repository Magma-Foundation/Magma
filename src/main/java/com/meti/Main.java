package com.meti;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            var root = Paths.get(".");
            var source = root.resolve("index.mgs");
            var application = new Application(source);
            application.run();
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }
}
