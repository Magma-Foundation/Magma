package com.meti.app;

import com.meti.nio.NIODirectory;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var path = Paths.get(".", "src", "main", "java");
        var root = new NIODirectory(path);
        var sourceGateway = new DirectoryGateway(root);
        new Application(sourceGateway).compileAll().consume(nioFileJavaSet -> System.out.println("Compiled " + nioFileJavaSet.size().value() + " files."), e -> {
            System.err.println("Failed to compile.");
            e.printStackTrace();
        });
    }
}
