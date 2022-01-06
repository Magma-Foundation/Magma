package com.meti;

import java.util.List;
import java.util.stream.Stream;

public class PathSource implements Source {
    private final NIOPath path;
    private final List<String> packageList;

    public PathSource(NIOPath path, List<String> packageList) {
        this.path = path;
        this.packageList = packageList;
    }

    @Override
    public String computeName() {
        return path.computeFileNameWithoutExtension();
    }

    @Override
    public Stream<String> computePackage() {
        return packageList.stream();
    }
}
