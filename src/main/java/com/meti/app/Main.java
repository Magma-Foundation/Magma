package com.meti.app;

import com.meti.java.JavaPath;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    static final Path Project = Paths.get(".", "project.json");

    public static void main(String[] args) {
        try {
            var path = new JavaPath(Project);
            var application = new Application(path);
            application.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
