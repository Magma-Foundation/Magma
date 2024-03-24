package com.meti;

import java.util.Optional;

public record ImportCompiler(String input) {
    Optional<String> compileImport() {
        if (input().startsWith("import ")) {
            var isStatic = input().startsWith("import static ");
            var separator = input().lastIndexOf('.');

            var parentStart = isStatic ? "import static ".length() : "import ".length();
            var parentName = input().substring(parentStart, separator);

            var childName = input().substring(separator + 1);
            var childString = isStatic && childName.equals("*")
                    ? "*"
                    : "{ " + childName + " }";

            return Optional.of("import " + childString + " from " + parentName + ";");
        }
        return Optional.empty();
    }
}