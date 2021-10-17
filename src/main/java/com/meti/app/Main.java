package com.meti.app;

import com.meti.java.NIOPath;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    static final Path Project = Paths.get(".", "project.json");

    public static void main(String[] args) {
        try {
            var path = new NIOPath(Project);
            var application = new Application(path);
            application.run();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
