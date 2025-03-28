package magma.java;

import magma.result.Result;
import magma.result.Results;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaFiles {
    public static Optional<IOException> writeString(Path target, String output) {
        try {
            Files.writeString(target, output);
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(e);
        }
    }

    public static Result<String, IOException> readSafe(Path source) {
        return Results.wrap(() -> Files.readString(source));
    }

    public static Result<Set<Path>, IOException> walk(Path directory) {
        return Results.wrap(() -> {
            try (Stream<Path> stream = Files.walk(directory)) {
                return stream.collect(Collectors.toSet());
            }
        });
    }

    public static Optional<IOException> createDirectoriesSafe(Path targetParent) {
        try {
            Files.createDirectories(targetParent);
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(e);
        }
    }
}
