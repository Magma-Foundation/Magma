package com.meti;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

public record NIOPath(Path value) {
    static final NIOPath Root = new NIOPath(Paths.get("."));

    String computeFileNameWithoutExtension() {
        var fileName = getValue().getFileName().toString();
        var separator = fileName.indexOf('.');
        return fileName.substring(0, separator);
    }

    void createAsDirectory() throws IOException {
        Files.createDirectory(getValue());
    }

    void delete() throws IOException {
        Files.deleteIfExists(getValue());
    }

    boolean exists() {
        return Files.exists(getValue());
    }

    public Path getValue() {
        return value;
    }

    void createAsFile() throws IOException {
        Files.createFile(getValue());
    }

    void deleteAsDirectory() throws IOException {
        Files.walkFileTree(getValue(), new DeletingVisitor());
    }

    Stream<Path> list() throws IOException {
        return Files.list(getValue());
    }

    NIOPath resolveChild(String name) {
        return new NIOPath(getValue().resolve(name));
    }

    NIOPath resolveSibling(String name) {
        return new NIOPath(getValue().resolveSibling(name));
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
