package com.meti.io;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.stream.Stream;

public record NIOPath(Path value) {
    public static final NIOPath Root = new NIOPath(Paths.get("."));

    public String computeFileNameWithoutExtension() {
        var fileName = value.getFileName().toString();
        var separator = fileName.indexOf('.');
        return fileName.substring(0, separator);
    }

    public void delete() throws IOException {
        Files.deleteIfExists(value);
    }

    public void deleteAsDirectory() throws IOException {
        Files.walkFileTree(value, new DeletingVisitor());
    }

    public void ensureAsDirectory() throws IOException {
        if (!exists()) createAsDirectory();
    }

    public boolean exists() {
        return Files.exists(value);
    }

    public void createAsDirectory() throws IOException {
        Files.createDirectory(value);
    }

    public NIOPath ensureAsFile() throws IOException {
        if (!exists()) createAsFile();
        return this;
    }

    public NIOPath createAsFile() throws IOException {
        Files.createFile(value);
        return this;
    }

    public Option<NIOPath> existing() {
        return exists()
                ? new Some<>(this)
                : new None<>();
    }

    public boolean hasExtensionOf(String expected) {
        var name = value.getFileName().toString();
        var separator = name.indexOf('.');
        var actual = name.substring(separator + 1);
        return expected.equals(actual);
    }

    public String readAsString() throws IOException {
        return Files.readString(value);
    }

    public NIOPath relativize(NIOPath path) {
        return new NIOPath(value.relativize(path.value));
    }

    public NIOPath resolveChild(String name) {
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

    public NIOPath toAbsolutePath() {
        return new NIOPath(this.value.toAbsolutePath());
    }

    public String asString(){
        return this.value.toString();
    }

    public Stream<NIOPath> walk() throws IOException {
        return Files.walk(value).map(NIOPath::new);
    }

    public NIOPath writeAsString(String value) throws IOException {
        Files.writeString(this.value, value);
        return this;
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
