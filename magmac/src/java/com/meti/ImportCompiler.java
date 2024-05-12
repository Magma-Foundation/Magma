package com.meti;

import java.util.Optional;

public record ImportCompiler(String stripped) implements Compiler {
    @Override
    public Optional<Result<String, CompileException>> compile() {
        if (!stripped().startsWith("import ")) return Optional.empty();

        var segmentsStart = stripped().startsWith("import static ") ? "import static ".length() : "import ".length();
        var segments = stripped().substring(segmentsStart);
        var separator = segments.lastIndexOf('.');
        if (separator == -1) return Optional.empty();

        var parent = segments.substring(0, separator);
        var child = segments.substring(separator + 1);
        return Optional.of(new Ok<>("import { " + child + " } from " + parent + ";\n"));
    }
}