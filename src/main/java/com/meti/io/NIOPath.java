package com.meti.io;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record NIOPath(java.nio.file.Path value) implements Path, Directory, File {
    public static final Path Root = new NIOPath(Paths.get("."));

    @Override
    public String asString() {
        return this.value.toString();
    }

    @Override
    public String computeFileNameWithoutExtension() {
        var fileName = value.getFileName().toString();
        var separator = fileName.indexOf('.');
        return fileName.substring(0, separator);
    }

    @Override
    public void createAsDirectory() throws IOException {
        Files.createDirectory(value);
    }

    @Override
    public File createAsFile() throws IOException {
        Files.createFile(value);
        return this;
    }

    @Override
    public Directory ensureAsDirectory() throws IOException {
        if (!exists()) createAsDirectory();
        return this;
    }

    @Override
    public File ensureAsFile() throws IOException {
        if (!exists()) createAsFile();
        return this;
    }

    @Override
    public Option<Directory> existingAsDirectory() {
        return exists() && Files.isDirectory(value)
                ? new Some<>(this)
                : new None<>();
    }

    @Override
    public Option<File> existingAsFile() {
        return exists()
                ? new Some<>(this)
                : new None<>();
    }

    @Override
    public boolean exists() {
        return Files.exists(value);
    }

    @Override
    public boolean hasExtensionOf(String expected) {
        var name = value.getFileName().toString();
        var separator = name.indexOf('.');
        var actual = name.substring(separator + 1);
        return expected.equals(actual);
    }

    @Override
    public Path relativize(Path path) {
        var names = path.streamNames().collect(Collectors.toList());

        var value = names.subList(1, names.size())
                .stream()
                .reduce(Paths.get(names.get(0)), java.nio.file.Path::resolve, (previous, next) -> next);

        return new NIOPath(this.value.relativize(value));
    }

    @Override
    public Path resolveChild(String name) {
        return new NIOPath(value.resolve(name));
    }

    @Override
    public Stream<String> streamNames() {
        var list = new ArrayList<String>();
        for (int i = 0; i < value.getNameCount(); i++) {
            var name = value.getName(i);
            list.add(name.toString());
        }
        return list.stream();
    }

    @Override
    public Path toAbsolutePath() {
        return new NIOPath(this.value.toAbsolutePath());
    }

    public void delete() throws IOException {
        Files.deleteIfExists(value);
    }

    @Override
    public void deleteAsDirectory() throws IOException {
        Files.walkFileTree(value, new DeletingVisitor());
    }

    @Override
    public Stream<Path> walk() throws IOException {
        return Files.walk(value).map(NIOPath::new);
    }

    @Override
    public String readAsString() throws IOException {
        return Files.readString(value);
    }

    @Override
    public Path writeAsString(String value) throws IOException {
        Files.writeString(this.value, value);
        return this;
    }

}
