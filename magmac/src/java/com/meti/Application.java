package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public record Application(SourceSet sourceSet) {
    void run() throws IOException {
        var set = this.sourceSet().collect();

        for (var path : set) {
            var package_ = path.findPackage();
            var name = path.findName();

            var target = package_.stream().reduce(Paths.get("."), Path::resolve, (path1, path2) -> path2).resolveSibling(name + ".mgs");
            Files.createFile(target);
        }
    }
}