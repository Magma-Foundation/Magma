package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

public class DirectoryGateway implements PathGateway {
    private final Path root;

    public DirectoryGateway(Path root) {
        this.root = root;
    }

    @Override
    public Path resolveChild(Source source) {
        return root.resolve(source.computeName() + ".java");
    }

    @Override
    public Result<Set<Source>> collectSources() {
        try (var stream = Files.walk(root)) {
            return Result.ok(stream
                    .filter(Files::isRegularFile)
                    .map(child -> new PathSource(root, child))
                    .collect(Collectors.toSet()));
        } catch (IOException e) {
            return Result.err(e);
        }
    }
}
