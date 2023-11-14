package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record Location(Path root, Path value) {
    String read() throws IOException {
        var path1 = value();
        var input = Files.readString(path1);
        return input;
    }

    String computeName() {
        var path = value();
        var fileName = path.getFileName().toString();
        var separator = fileName.indexOf('.');
        return fileName.substring(0, separator);
    }

    public List<String> computePackage() {
        var relative = root.relativize(value);
        return IntStream.range(0, relative.getNameCount() - 1)
                .mapToObj(relative::getName)
                .map(Path::toString)
                .collect(Collectors.toList());
    }
}
