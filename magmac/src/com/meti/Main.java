package com.meti;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            var sourceDirectory = Paths.get(".", "magmac", "src");
            var source = new DirectorySource(sourceDirectory);
            new Application(source).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
