package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Script {
    private final Path path;

    public Script(Path path) {
        this.path = path;
    }

    Path extend(String name, String extension) {
        return path.resolveSibling(name + extension);
    }

    public boolean exists() {
        return Files.exists(path);
    }

    String extractName() {
        var nativeName = nativeName();
        var separator = nativeName.indexOf('.');
        return separator == -1 ? nativeName :
                nativeName.substring(0, separator);
    }

    private String nativeName() {
        var lastIndex = path.getNameCount() - 1;
        return path.getName(lastIndex).toString();
    }

    public String asString() {
        return path.toAbsolutePath().toString();
    }

    public boolean hasExtensionOf(String extension) {
        var nativeName = nativeName();
        var separator = nativeName.indexOf('.');
        return separator != -1 && nativeName.substring(separator + 1).equals(extension);
    }

    public String read() throws IOException {
        return Files.readString(path);
    }
}
