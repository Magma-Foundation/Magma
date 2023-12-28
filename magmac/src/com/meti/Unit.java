package com.meti;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Unit(Path source, Path root) {
    List<String> getSegments() {
        var relativeToRoot = root.relativize(source);
        var count = relativeToRoot.getNameCount();
        if (count == 0) return Collections.emptyList();

        var list = new ArrayList<String>();
        for (int i = 0; i < count - 1; i++) {
            list.add(relativeToRoot.getName(i).toString());
        }

        return list;
    }

    String getFileName() {
        var fileNameAsString = source().getFileName().toString();
        var separator = fileNameAsString.indexOf('.');
        return separator == -1 ? fileNameAsString : fileNameAsString.substring(0, separator);
    }
}