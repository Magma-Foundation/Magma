package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;

public class Script {
    private final Path source;

    public Script(Path source) {
        this.source = source;
    }

    Path extend(String name, String extension) {
        return source.resolveSibling(name + extension);
    }

    public boolean exists() {
        return Files.exists(source);
    }

    String extractName() {
        var nativeName = nativeName();
        var separator = nativeName.indexOf('.');
        return separator == -1 ? nativeName :
                nativeName.substring(0, separator);
    }

    private String nativeName() {
        var lastIndex = source.getNameCount() - 1;
        return source.getName(lastIndex).toString();
    }

    public String asString() {
        return source.toAbsolutePath().toString();
    }

    public boolean hasExtensionOf(String extension) {
        var nativeName = nativeName();
        var separator = nativeName.indexOf('.');
        return separator != -1 && nativeName.substring(separator + 1).equals(extension);
    }
}
