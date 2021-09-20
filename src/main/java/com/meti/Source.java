package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;

public class Source {
    private final Path source;

    public Source(Path source) {
        this.source = source;
    }

    Path extend(String name, String extension) {
        return source.resolveSibling(name + extension);
    }

    boolean exists() {
        return Files.exists(source);
    }

    String extractName() {
        var lastIndex = source.getNameCount() - 1;
        var nativeName = source.getName(lastIndex)
                .toString();
        var separator = nativeName.indexOf('.');
        return separator == -1 ?
                nativeName :
                nativeName.substring(0, separator);
    }
}
