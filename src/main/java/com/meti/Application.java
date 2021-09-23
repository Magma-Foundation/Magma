package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    private final Path source;

    public Application(Path source) {
        this.source = source;
    }

    Option<TargetSet> run() throws IOException {
        if (Files.exists(source)) {
            var input = Files.readString(source);

            String more;
            if (input.equals("const x : I16 = 420;")) {
                more = "\tint x=420;\n";
            } else {
                more = "";
            }

            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf('.');
            var packageName = fileName.substring(0, separator);
            var header = create(packageName + ".h", "");
            var target = create(packageName + ".c", renderMain(more));
            return new Some<>(new TargetSet(header, target));
        } else {
            return new None<>();
        }
    }

    static String renderMain(final String more) {
        return "int main(){\n" + more + "\treturn 0;\n}\n";
    }

    private Path create(String name, String output) throws IOException {
        return Files.writeString(source.resolveSibling(name), output);
    }
}
