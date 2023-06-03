package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirectoryGateway implements PathGateway {
    private final Path root;

    public DirectoryGateway(Path root) {
        this.root = root;
    }

    @Override
    public Result collectSources() {
        try (var stream = Files.list(root)) {
            var children = stream.collect(Collectors.toSet());
            return Result.ok(children);
        } catch (IOException e) {
            return Result.err(e);
        }
    }
}
