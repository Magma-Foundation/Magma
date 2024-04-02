package com.meti;

import com.meti.io.DirectorySourceSet;
import com.meti.io.SourceSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            run(new DirectorySourceSet(Paths.get("./src/java")), Paths.get("./src/magma"));
            run(new DirectorySourceSet(Paths.get("./test/java")), Paths.get("./test/magma"));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    static void run(SourceSet sourceSet, Path targetDirectory) throws IOException {
        var set = sourceSet.collect();

        for (var source : set) {
            var namespace = source.findNamespace();
            var name = source.findName();

            var input = source.read();
            var output = Compiler.compile(input);

            var parent = namespace.stream().reduce(targetDirectory, Path::resolve, (path, path2) -> path2);
            if(!Files.exists(parent)) Files.createDirectories(parent);

            var target = parent.resolve(name + ".mgs");
            Files.writeString(target, output);
        }
    }
}
