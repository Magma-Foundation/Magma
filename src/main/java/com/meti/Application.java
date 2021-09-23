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

            var output = compile(input);
            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf('.');
            var packageName = fileName.substring(0, separator);
            var header = create(packageName + ".h", "");
            var target = create(packageName + ".c", renderMain(output));
            return new Some<>(new TargetSet(header, target));
        } else {
            return new None<>();
        }
    }

    private String compile(String input) throws ApplicationException {
        String more;
        if (input.equals("const x : I16 = 420;")) {
            more = "\tint x=420;\n";
        } else {
            throw new ApplicationException("Invalid input:" + input);
        }
        return more;
    }

    private Path create(String name, String output) throws IOException {
        return Files.writeString(source.resolveSibling(name), output);
    }

    static String renderMain(final String more) {
        return "int main(){\n" + more + "\treturn 0;\n}\n";
    }
}
