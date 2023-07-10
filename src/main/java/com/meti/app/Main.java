package com.meti.app;

import com.meti.nio.NIODirectory;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var sourcePath = Paths.get(".", "src", "main", "java");
        var sourceRoot = new NIODirectory(sourcePath);
        var sourceGateway = new DirectorySources(sourceRoot);

        var targetPath = Paths.get(".", "target", "");
        var targetRoot = new NIODirectory(targetPath);
        var targetGateway = new DirectoryTargets(targetRoot);

        new Application(sourceGateway, targetGateway).compileAll().consume(nioFileJavaSet -> System.out.println("Compiled " + nioFileJavaSet.size().value() + " files."), e -> {
            System.err.println("Failed to compile.");
            e.printStackTrace();
        });
    }
}
