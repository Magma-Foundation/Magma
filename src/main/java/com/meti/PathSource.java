package com.meti;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record PathSource(Path parent, Path child) implements Source {
    @Override
    public List<String> computeNamespace() {
        var relative = parent.relativize(child);
        return IntStream.range(0, relative.getNameCount() - 1)
                .mapToObj(relative::getName)
                .map(Path::toString)
                .collect(Collectors.toList());
    }

    @Override
    public String computeName() {
        var fileName = child.getFileName().toString();
        var separator = fileName.indexOf('.');
        return fileName.substring(0, separator);
    }
}
