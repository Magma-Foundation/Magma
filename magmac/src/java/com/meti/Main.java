package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            run(new DirectorySourceSet(Paths.get("./Personal/Magma/magmac/src/java")), Paths.get("./Personal/Magma/magmac/dist/src/java"));
            run(new DirectorySourceSet(Paths.get("./Personal/Magma/magmac/test/java")), Paths.get("./Personal/Magma/magmac/dist/test/java"));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    static void run(SourceSet sourceSet, Path targetDirectory) throws IOException {
        var set = sourceSet.collect();

        for (var source1 : set) {
            var namespace = source1.findNamespace();
            var name = source1.findName();

            var input = source1.read();
            var output = Compiler.compile(input);

            var parent = namespace.stream().reduce(targetDirectory, Path::resolve, (path, path2) -> path2);
            if(!Files.exists(parent)) Files.createDirectories(parent);

            var target = parent.resolve(name + ".mgs");
            Files.writeString(target, output);
        }
    }
}
