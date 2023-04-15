package com.meti;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        var files = new ArrayList<Path>();
        var sourceDirectory = Paths.get(".", "src");
        Files.walkFileTree(sourceDirectory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
              files.add(file);
              return FileVisitResult.CONTINUE;
            }
        });

        for (Path file : files) {
            var relative = sourceDirectory.relativize(file);
            var leaf = Paths.get(".", "target")
                    .resolve(relative);

            var parent = leaf.getParent();
            if(!Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            Files.createFile(leaf);
        }
    }
}