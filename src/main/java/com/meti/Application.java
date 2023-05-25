package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;

public record Application(VolatileSingleSource source) {
    static Path run(Path source) throws IOException {
        var input = Files.readString(source);
        var output = new Compiler(input).compile();

        var fileName = source.getFileName().toString();
        var separator = fileName.indexOf('.');
        var fileNameWithoutExtension = fileName.substring(0, separator);
        var target = source.resolveSibling(fileNameWithoutExtension + ".mgs");
        Files.writeString(target, output);
        return target;
    }

    Optional<Path> runExceptionally() throws IOException {
        var sources = source().collectSources();
        var targets = new HashSet<Path>();

        for (var o : sources) {
            var target = run(o);
            targets.add(target);
        }

        return targets.stream().findAny();
    }
}