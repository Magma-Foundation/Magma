package com.meti;

import java.nio.file.Path;

public abstract class PathGateway implements Gateway {
    protected final Path path;

    public PathGateway(Path path) {
        this.path = path;
    }

    @Override
    public Path resolvePackage(Package package_) {
        var parent = path;
        var namespace = package_.computeNamespace();
        for (String s : namespace) {
            parent = parent.resolve(s);
        }

        var fileName = package_.computeName() + ".mgs";
        return parent.resolve(fileName);
    }
}
