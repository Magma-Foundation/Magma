package com.meti;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        new Application(Paths.get(".", "Main.java")).run();
    }
}
