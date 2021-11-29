package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;

public record DirectoryClassPath(Path root) implements ClassPath {
    @Override
    public boolean isDefined(String name) {
        return Files.isRegularFile(root.resolve(name + ".mgf"));
    }
}
