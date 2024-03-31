package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public record PathSource(Path root, Path child) {
    String findName() {
        var fileName = child.getFileName().toString();
        var separator = fileName.indexOf('.');
        return fileName.substring(0, separator);
    }

    List<String> findNamespace() {
        var list = new ArrayList<String>();
        var relative = root.relativize(child);
        for (int i = 0; i < relative.getNameCount() - 1; i++) {
            list.add(relative.getName(i).toString());
        }
        return list;
    }

    public String read() throws IOException {
        return Files.readString(child);
    }
}
