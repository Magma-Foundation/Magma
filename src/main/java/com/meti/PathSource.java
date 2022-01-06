package com.meti;

import java.util.List;
import java.util.stream.Stream;

public record PathSource(NIOPath path, List<String> packageList) implements Source {

    @Override
    public String computeName() {
        return path.computeFileNameWithoutExtension();
    }

    @Override
    public Stream<String> computePackage() {
        return packageList.stream();
    }
}
