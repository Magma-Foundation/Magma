package com.meti;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
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

    public boolean hasExtensionOf(String expected) {
        var name = value.getFileName().toString();
        var separator = name.indexOf('.');
        var actual = name.substring(separator + 1);
        return expected.equals(actual);
    }

    public NIOPath relativize(NIOPath path) {
        return new NIOPath(value.relativize(path.value));
    }

    NIOPath resolveChild(String name) {
        return new NIOPath(value.resolve(name));
    }

    public Stream<String> streamNames() {
        var list = new ArrayList<String>();
        for (int i = 0; i < value.getNameCount(); i++) {
            var name = value.getName(i);
            list.add(name.toString());
        }
        return list.stream();
    }

    public Stream<NIOPath> walk() throws IOException {
        return Files.walk(value).map(NIOPath::new);
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
