package magma.io;

import magma.Main;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Files {
    public static Result<Set<Path>, IOError> walk() {
        try (Stream<Path> stream = java.nio.file.Files.walk(Main.SOURCE_DIRECTORY)) {
            return new Ok<>(stream.collect(Collectors.toSet()));
        } catch (IOException e) {
            return new Err<>(new ExceptionalIOError(e));
        }
    }

    public static Result<String, IOError> readString(Path source) {
        try {
            return new Ok<>(java.nio.file.Files.readString(source));
        } catch (IOException e) {
            return new Err<>(new ExceptionalIOError(e));
        }
    }

    public static Optional<IOError> writeString(Path path, String output) {
        try {
            java.nio.file.Files.writeString(path, output);
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(new ExceptionalIOError(e));
        }
    }

    public static Optional<IOError> createDirectories(Path targetParent) {
        try {
            java.nio.file.Files.createDirectories(targetParent);
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(new ExceptionalIOError(e));
        }
    }
}