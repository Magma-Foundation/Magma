package com.meti.source;

import com.meti.io.File;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public record FileSource(File file, List<String> packageList) implements Source {
    @Override
    public String computeName() {
        return file.computeFileNameWithoutExtension();
    }

    @Override
    public Stream<String> computePackage() {
        return packageList.stream();
    }

    @Override
    public String read() throws IOException {
        return file.readAsString();
    }
}
