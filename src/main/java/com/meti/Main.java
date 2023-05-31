package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            Files.writeString(Paths.get(".", "Main.mgs"), "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
