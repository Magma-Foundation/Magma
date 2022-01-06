package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    private final Module module;

    public Application(Module module) {
        this.module = module;
    }

    void run() throws IOException {
        var sources = module.listSources();

        for (Path source : sources) {
            compile(source);
        }
    }

    private void compile(Path source) throws IOException {
        var fileName = source.getFileName().toString();
        var separator = fileName.indexOf('.');
        var name = fileName.substring(0, separator);
        Files.createFile(source.resolveSibling(name + ".c"));
    }
}
