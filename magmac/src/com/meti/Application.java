package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class Application {
    private final Source source;
    private final Path targetDirectory;

    public Application(Source source, Path targetDirectory) {
        this.source = source;
        this.targetDirectory = targetDirectory;
    }

    private static Set<Path> fold(Set<Path> set, Path next) {
        set.add(next);
        return set;
    }

    Optional<Path> runSingle() throws IOException {
        return run().stream().findFirst();
    }

    public Set<Path> run() throws IOException {
        return source.streamUnits()
                .map(this::compileImpl)
                .reduce(Ok.<Set<Path>, IOException>apply(new HashSet<>()),
                        (set, element) -> set.and(element, Application::fold),
                        (previous, next) -> next).unwrap();
    }

    private Result<Path, IOException> compileImpl(Unit unit) {
        try {
            var segments = unit.getSegments();
            var fileName = unit.getFileName();

            var parentDirectory = segments.stream().reduce(targetDirectory, Path::resolve, (previous, next) -> next);
            if (!Files.exists(parentDirectory)) {
                Files.createDirectories(parentDirectory);
            }

            var target = parentDirectory.resolveSibling(fileName + ".mgs");
            Files.createFile(target);
            return Ok.apply(target);
        } catch (IOException e) {
            return Err.apply(e);
        }
    }
}