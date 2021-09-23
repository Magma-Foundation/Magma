package com.meti;

import com.meti.node.Function;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

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
            var output = compileOutput().replace(";", ";\n\t");
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

    private String compileOutput() throws IOException, ApplicationException {
        var input = Files.readString(source);
        return new Compiler(input).compile();
    }

    private Path create(String name, String output) throws IOException {
        return Files.writeString(source.resolveSibling(name), output);
    }
}
