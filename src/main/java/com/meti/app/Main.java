package com.meti.app;

import com.meti.java.Set;
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

        new Application(sourceGateway, targetGateway).compileAll().consume(Main::onSuccess, Main::onErr);
    }

    private static void onErr(CompileException e) {
        System.err.println("Failed to compile.");
        e.printStackTrace();
    }

    private static void onSuccess(Set<NIOTarget> set) {
        System.out.println("Compiled " + set.size().value() + " files.");
    }
}
