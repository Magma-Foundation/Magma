package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class DirectoryGateway implements PathGateway {
    private final Path root;

    public DirectoryGateway(Path root) {
        this.root = root;
    }

    @Override
    public Path resolveChild(Source source) {
        var reduce = source.computeNamespace()
                .stream()
                .reduce(root, Path::resolve, (path, path2) -> path2);
        return reduce.resolve(source.computeName() + ".java");
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
