package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public final class Application {
    private final SourceSet sourceSet;
    private final Path targetRoot;
    private final String targetExtension;

    public Application(SourceSet sourceSet, Path targetRoot, String targetExtension) {
        this.sourceSet = sourceSet;
        this.targetRoot = targetRoot;
        this.targetExtension = targetExtension;
    }

    void run() throws IOException {
        var set = this.sourceSet().collect();

        for (var path : set) {
            var package_ = path.findPackage();
            var name = path.findName();

            var target = package_.stream()
                    .reduce(targetRoot, Path::resolve, (path1, path2) -> path2)
                    .resolve(name + targetExtension);

            var parent = target.getParent();
            if (!Files.exists(parent)) Files.createDirectories(parent);
            if (!Files.exists(target)) Files.createFile(target);
        }
    }

    public SourceSet sourceSet() {
        return sourceSet;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Application) obj;
        return Objects.equals(this.sourceSet, that.sourceSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceSet);
    }

    @Override
    public String toString() {
        return "Application[" +
               "sourceSet=" + sourceSet + ']';
    }

}