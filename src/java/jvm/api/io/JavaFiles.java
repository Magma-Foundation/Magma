package jvm.api.io;

import jvm.api.result.JavaResults;
import magma.api.option.Option;
import magma.api.result.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaFiles {
    public static Option<IOException> writeString(Path target, String output) {
       return JavaResults.wrapRunnable(() -> {
           Files.writeString(target, output);
       });
    }

    public static Result<Set<Path>, IOException> walk(Path directory) {
        return JavaResults.wrapSupplier(() -> {
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
