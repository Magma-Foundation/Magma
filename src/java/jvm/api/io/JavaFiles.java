package jvm.api.io;

import jvm.api.result.JavaResults;
import magma.api.io.IOError;
import magma.api.option.Option;
import magma.api.result.Result;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaFiles {
    public static Option<IOError> writeString(Path target, String output) {
        return JavaResults.wrapRunnable(() -> Files.writeString(target, output)).map(JavaIOError::new);
    }

    public static Result<Set<Path>, IOError> walk(Path directory) {
        return JavaResults.wrapSupplier(() -> {
            try (Stream<Path> stream = Files.walk(directory)) {
                return stream.collect(Collectors.toSet());
            }
        }).mapErr(JavaIOError::new);
    }

    public static Option<IOError> createDirectoriesSafe(Path targetParent) {
        return JavaResults.wrapRunnable(() -> Files.createDirectories(targetParent)).map(JavaIOError::new);
    }
}
