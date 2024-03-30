package com.meti.java;

import java.nio.file.Files;
import java.nio.file.Paths;

public class JavaApplication {
    public static boolean exists() {
        return Files.exists(Paths.get(".", "ApplicationTest.mgs"));
    }
}