package com.meti;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

public record NIOPath(Path value) {
    static final NIOPath Root = new NIOPath(Paths.get("."));

    String computeFileNameWithoutExtension() {
        var fileName = value.getFileName().toString();
        var separator = fileName.indexOf('.');
        return fileName.substring(0, separator);
    }

    void createAsDirectory() throws IOException {
        Files.createDirectory(value);
    }

    void createAsFile() throws IOException {
        Files.createFile(value);
    }

    void delete() throws IOException {
        Files.deleteIfExists(value);
    }

    void deleteAsDirectory() throws IOException {
        Files.walkFileTree(value, new DeletingVisitor());
    }

    boolean exists() {
        return Files.exists(value);
    }

    Stream<Path> list() throws IOException {
        return Files.list(value);
    }

    NIOPath resolveChild(String name) {
        return new NIOPath(value.resolve(name));
    }

    NIOPath resolveSibling(String name) {
        return new NIOPath(value.resolveSibling(name));
    }

    private static class DeletingVisitor implements FileVisitor<Path> {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
    }
}
