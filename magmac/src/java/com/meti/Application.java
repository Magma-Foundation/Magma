package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public final class Application {
    private final SourceSet sourceSet;
    private final Path targetRoot;
    private final List<String> targetExtensions;

    public Application(SourceSet sourceSet, Path targetRoot, String... targetExtensions) {
        this.sourceSet = sourceSet;
        this.targetRoot = targetRoot;
        this.targetExtensions = List.of(targetExtensions);
    }

    void run() throws IOException {
        var set = this.sourceSet().collect();

        for (var path : set) {
            var input = path.read();
            var package_ = path.findPackage();
            var name = path.findName();

            for (String targetExtension : targetExtensions) {
                var target = package_.stream()
                        .reduce(targetRoot, Path::resolve, (path1, path2) -> path2)
                        .resolve(name + targetExtension);

                var parent = target.getParent();
                if (!Files.exists(parent)) Files.createDirectories(parent);

                var states = new Compiler(input, sourceSet.findExtension(), targetExtension).compile();
                var main = states.stream().map(state -> state.value).collect(Collectors.joining());

                var imports = states.stream()
                        .map(state -> state.importString)
                        .flatMap(Optional::stream)
                        .collect(Collectors.joining());

                var structs = states.stream()
                        .map(state -> state.structs)
                        .flatMap(Optional::stream)
                        .collect(Collectors.joining());

                var exports = states.stream()
                        .map(state -> state.exports)
                        .flatMap(Optional::stream)
                        .toList();

                var joinedExports = exports.stream()
                        .map(value -> "\n\t" + value)
                        .collect(Collectors.joining(""));

                String exportString;
                if (joinedExports.isEmpty()) {
                    exportString = "";
                } else {
                    exportString = "\nmodule.exports = {" + joinedExports + "\n};";
                }

                String tempOutput = imports + structs + main + exportString;
                String output;
                if (targetExtension.equals(".h")) {
                    output = "#ifndef " + name + "_H\n#define " + name + "_H" + tempOutput + "\n#endif";
                } else if (targetExtension.equals(".c")) {
                    output = "#include \"" + name + ".c" + "\"" + tempOutput;
                } else {
                    output = tempOutput;
                }
                Files.writeString(target, output);
            }
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

    static final class State {
        private final Optional<String> importString;
        private final String value;
        private final Optional<String> exports;
        private final Optional<String> structs;

        State(String value, Optional<String> exports) {
            this(value, exports, Optional.empty());
        }

        State(String value, Optional<String> exports, Optional<String> importString) {
            this(importString, value, exports, Optional.empty());
        }

        State(Optional<String> importString, String value, Optional<String> exports, Optional<String> structs) {
            this.importString = importString;
            this.value = value;
            this.exports = exports;
            this.structs = structs;
        }

        public String value() {
            return value;
        }

        public Optional<String> exports() {
            return exports;
        }
    }
}