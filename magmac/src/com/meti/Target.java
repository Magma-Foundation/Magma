package com.meti;

import java.nio.file.Path;

public record Target(Path targetRoot) {
    Path resolveTarget(Location location, String extension) {
        var package_ = location.computePackage();
        var name = location.computeName();
        var target = package_.stream()
                .reduce(targetRoot(), Path::resolve, (previous, next) -> next)
                .resolve(name + extension);
        return target;
    }
}