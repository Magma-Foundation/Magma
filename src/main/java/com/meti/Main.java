package com.meti;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            var source = Paths.get(".", "index.mgs");
            var application = new Application(source);
            application.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
