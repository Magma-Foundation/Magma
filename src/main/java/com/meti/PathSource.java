package com.meti;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.StreamSupport;

public record PathSource(Path root, Path value) {

    Package findPackage() {
        var relativeValue = root.relativize(value);
        var namespace = new ArrayList<String>();
        for (int i = 0; i < relativeValue.getNameCount() - 1; i++) {
            namespace.add(relativeValue.getName(i).toString());
        }

        var fileName = value().getFileName().toString();
        var separator = fileName.indexOf('.');
        return new ListPackage(namespace, fileName.substring(0, separator));
    }
}
