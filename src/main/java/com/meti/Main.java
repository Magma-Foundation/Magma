package com.meti;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            new Application(Paths.get(".", "index.mg")).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
