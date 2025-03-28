package jvm.api.io;

import jvm.api.result.JavaResults;
import magma.api.collect.Stream;
import magma.api.io.IOError;
import magma.api.io.Path_;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record JavaPath(Path path) implements Path_ {
    @Override
    public boolean isExists() {
        return Files.exists(path);
    }

    @Override
    public Stream<String> stream() {
        List<String> segments = new ArrayList<>();
        for (int i = 0; i < path.getNameCount(); i++) {
            segments.add(path.getName(i).toString());
        }

        return new JavaList<>(segments).stream();
    }

    @Override
    public Path_ resolve(String segment) {
        return new JavaPath(path.resolve(segment));
    }

    @Override
    public Path_ relativize(Path_ child) {
        return new JavaPath(path.relativize(JavaFiles.unwrap(child)));
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
    public String readString() throws IOException {
        return Files.readString(path);
    }

    @Override
    public Option<Path_> getParent() {
        Path parent = path.getParent();
        return parent == null ? new None<>() : new Some<>(new JavaPath(parent));
    }

    @Override
    public Path_ last() {
        return new JavaPath(path.getFileName());
    }

    @Override
    public Option<IOError> writeString(String output) {
        return JavaResults.wrapRunnable(() -> Files.writeString(JavaFiles.unwrap(this), output)).map(JavaIOError::new);
    }

    @Override
    public Result<Set<Path_>, IOError> walk() {
        return JavaResults.wrapSupplier(this::walkExceptionally).mapErr(JavaIOError::new);
    }

    @Override
    public Option<IOError> createDirectoriesSafe() {
        return JavaResults
                .wrapRunnable(() -> Files.createDirectories(JavaFiles.unwrap(this)))
                .map(JavaIOError::new);
    }

    @Override
    public Set<Path_> walkExceptionally() throws IOException {
        try (java.util.stream.Stream<Path> stream = Files.walk(path)) {
            return stream.<Path_>map(JavaPath::new)
                    .collect(Collectors.toSet());
        }
    }
}
