package jvm.io;

import jvm.collect.set.Sets;
import magma.collect.set.Set_;
import magma.collect.stream.HeadedStream;
import magma.collect.stream.RangeHead;
import magma.collect.stream.Stream;
import magma.io.Path_;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public record NIOPath(Path path) implements Path_ {
    @Override
    public void writeString(String output) throws IOException {
        Files.writeString(path, output);
    }

    @Override
    public void createAsDirectories() throws IOException {
        Files.createDirectories(path);
    }

    @Override
    public Path_ resolve(String child) {
        return new NIOPath(path.resolve(child));
    }

    @Override
    public Stream<String> stream() {
        return new HeadedStream<>(new RangeHead(path.getNameCount()))
                .map(path::getName)
                .map(Path::toString);
    }

    @Override
    public Path_ relativize(Path_ child) {
        return new NIOPath(path.relativize(Paths.toNative(child)));
    }

    @Override
    public boolean isRegularFile() {
        return Files.isRegularFile(path);
    }

    @Override
    public String asString() {
        return path.toString();
    }

    @Override
    public Path_ getParent() {
        return new NIOPath(path.getParent());
    }

    @Override
    public Path_ getFileName() {
        return new NIOPath(path.getFileName());
    }

    @Override
    public String readString() throws IOException {
        return Files.readString(path);
    }

    @Override
    public Set_<Path_> walk() throws IOException {
        try (java.util.stream.Stream<Path> stream = Files.walk(path)) {
            return Sets.fromNative(stream
                    .map(NIOPath::new)
                    .collect(Collectors.toSet()));
        }
    }
}