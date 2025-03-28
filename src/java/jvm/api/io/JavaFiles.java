package jvm.api.io;

import magma.api.io.Path_;

import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaFiles {
    public static final Path DEFAULT_PATH = Paths.get(".");

    public static Path unwrap(Path_ path) {
        // This method will be unexpected if the path is somehow empty.
        return path.stream()
                .foldWithMapper(Paths::get, Path::resolve)
                .orElse(DEFAULT_PATH);
    }
}
