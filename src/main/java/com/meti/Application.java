package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    private final Path source;

    public Application(Path source) {
        this.source = source;
    }

    Option<TargetSet> run() throws IOException, ApplicationException {
        if (Files.exists(source)) {
            var input = Files.readString(source);
            var output = new Compiler(input).compile();

            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf('.');
            var packageName = fileName.substring(0, separator);
            var header = create(packageName + ".h", "");
            var target = create(packageName + ".c", new Function(output).render());
            return new Some<>(new TargetSet(header, target));
        } else {
            return new None<>();
        }
    }

    private Path create(String name, String output) throws IOException {
        return Files.writeString(source.resolveSibling(name), output);
    }
}
