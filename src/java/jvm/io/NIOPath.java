package jvm.io;

import jvm.collect.set.Sets;
import magma.collect.set.Set_;
import magma.collect.stream.HeadedStream;
import magma.collect.stream.RangeHead;
import magma.collect.stream.Stream;
import magma.io.IOError;
import magma.io.Path_;
import magma.option.Option;
import magma.result.Result;
import jvm.result.Results;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public record NIOPath(Path path) implements Path_ {
    private void writeString0(String output) throws IOException {
        Files.writeString(path, output);
    }

    private void createAsDirectories0() throws IOException {
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

    private String readString0() throws IOException {
        return Files.readString(path);
    }

    private Set_<Path_> walk0() throws IOException {
        try (java.util.stream.Stream<Path> stream = Files.walk(path)) {
            return Sets.fromNative(stream
                    .map(NIOPath::new)
                    .collect(Collectors.toSet()));
        }
    }

    @Override
    public boolean exists() {
        return Files.exists((Paths.toNative(this)));
    }

    @Override
    public Result<Set_<Path_>, IOError> walk() {
        return Results.wrapSupplier(this::walk0).mapErr(JavaIOError::new);
    }

    @Override
    public Option<IOError> writeString(String output) {
        return Results.wrapRunnable(() -> writeString0(output))
                .map(JavaIOError::new);
    }

    @Override
    public Option<IOError> createAsDirectories() {
        return Results.wrapRunnable(this::createAsDirectories0).map(JavaIOError::new);
    }

    @Override
    public Result<String, IOError> readString() {
        return Results.wrapSupplier(this::readString0)
                .mapErr(JavaIOError::new);
    }
}