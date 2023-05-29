package com.meti;

import java.nio.file.Path;
import java.util.Collections;

public record PathSource(Path value) {

    Package findPackage() {
        var fileName = value().getFileName().toString();
        var separator = fileName.indexOf('.');
        return new ListPackage(Collections.emptyList(), fileName.substring(0, separator));
    }
}
