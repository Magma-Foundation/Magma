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

    private Result<Set<Path>> collectSources1() {
        try (var stream = Files.list(root)) {
            var children = stream.collect(Collectors.toSet());
            return Result.ok(children);
        } catch (IOException e) {
            return Result.err(e);
        }
    }

    @Override
    public Path resolveChild(Source aSource) {
        return root.resolve(aSource.computeName() + ".java");
    }

    @Override
    public Result<Set<Source>> collectSources() {
        return collectSources1().mapValue(sources1 -> sources1.stream()
                .map(PathGateway::createSourceFromPath)
                .collect(Collectors.toSet()));
    }
}
