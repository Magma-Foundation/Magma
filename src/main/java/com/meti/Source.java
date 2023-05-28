package com.meti;

import java.nio.file.Path;

public record Source(Path value) {
    String createPackage() {
        var fileName = value().getFileName().toString();
        var separator = fileName.indexOf('.');
        return fileName.substring(0, separator);
    }
}
