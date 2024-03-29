package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public record PathSource(Path root, Path location) {
    String findName() {
        var name = location().getFileName().toString();
        var index = name.indexOf('.');
        if (index == -1) {
            throw new IllegalStateException("Location '" + location + "' does not have a name?");
        }

        return name.substring(0, index);
    }

    public List<String> findNamespace() {
        var list = new ArrayList<String>();
        var relativeLocation = root.relativize(location);
        var namespaceLength = relativeLocation.getNameCount() - 1;
        for (int i = 0; i < namespaceLength; i++) {
            var nameSegment = relativeLocation.getName(i).toString();
            list.add(nameSegment);
        }
        return list;
    }

    public String read() throws IOException {
        return Files.readString(location);
    }
}
