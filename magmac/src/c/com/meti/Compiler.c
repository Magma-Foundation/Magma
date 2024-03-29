#include "Compiler.c"from java.utilfrom java.utilfrom java.utilfrom java.utilfrom java.util.streampublic record Compiler(String input, String sourceExtension, String targetExtension) {
    static Optional<String> compileImport(String line, String sourceExtension, String targetExtension) {
        if (line.startsWith("import ")) {
            var segmentString = line.substring("import ".length()).strip();

            var childStart = segmentString.indexOf('{');
            var childEnd = segmentString.indexOf('}');

            if (sourceExtension.equals(".java")) {
                var arrays = Arrays.asList(segmentString.split("\\."));
                if (arrays.isEmpty()) return Optional.of(line);
                if (arrays.size() == 1) return Optional.of("import " + arrays.get(0));

                var child = arrays.get(arrays.size() - 1);
                var parent = String.join(".", arrays.subList(0, arrays.size() - 1));
                return Optional.ofNullable("\nimport { %s } from %s;".formatted(child, parent));
            } else if (targetExtension.equals(".js")) {
                var child = segmentString.substring(childStart + 1, childEnd).strip();
                var fromIndex = segmentString.indexOf("from ", childEnd);
                if (fromIndex == -1) return Optional.of(line);

                var parent = segmentString.substring(fromIndex + "from ".length()).strip();
                return Optional.of("\nconst { " + child + " } = require(\"" + parent + "\");");
            } else if (targetExtension.equals(".c")) {
                return Optional.of("");
            } else if (targetExtension.equals(".h")) {
                var fromIndex = segmentString.indexOf("from ", childEnd);
                if (fromIndex == -1) return Optional.of(line);

                var parent = segmentString.substring(fromIndex + "from ".length()).strip();
                return Optional.of("\n#include <" + parent + ".h>");
            } else {
                return Optional.of(line);
            }
        }
        return Optional.empty();
    }

    List<Application.State> compile() {
        var lines = split(input());

        if (sourceExtension().equals(".java")) {
            lines.removeIf(line -> line.startsWith("package"));
        }

        return lines.stream()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .map(this::compileLine)
                .collect(Collectors.toList());
    }

    private Application.State compileLine(String line) {
        var compiled = compileImport(line, sourceExtension(), targetExtension());
        if (compiled.isPresent()) return new Application.State("", Optional.empty(), compiled);

        if (line.startsWith("export class def ")) {
            var paramStart = line.indexOf('(');
            var name = line.substring("export class def ".length(), paramStart).strip();
            var body = line.substring(line.indexOf('{') + 1, line.lastIndexOf('}'));

            if (targetExtension().equals(".js")) {
                return new Application.State("\nfunction " + name + "(){" + body + "\treturn {};\n}", Optional.of(name));
            }

            if (targetExtension().equals(".h")) {
                return new Application.State("\nvoid " + name + "();", Optional.empty(),
                        Optional.of("\nstruct " + name + " {}"));
            } else if (targetExtension().equals(".c")) {
                return new Application.State("\nstruct " + name + " " + name + "(){" + body +
                                             "\n\tstruct " + name + " " + "this" + " = {};" +
                                             "\n\treturn this;" +
                                             "\n}", Optional.empty());
            }
        }

        if (line.startsWith("public class ")) {
            var name = line.substring("public class ".length(), line.indexOf('{')).strip();
            var body = line.substring(line.indexOf('{') + 1, line.lastIndexOf('}')).strip();
            var bodyString = split(body);
            String newBody = "";
            for (String s : bodyString) {
                newBody += "\n\tdef test() => {}";
            }

            return new Application.State("\nexport class def " + name + "() => {" + newBody + "\n}", Optional.empty());
        }

        return new Application.State(line, Optional.empty());
    }

    private ArrayList<String> split(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else if (c == '}' && depth == 1) {
                builder.append(c);
                depth--;
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }

        lines.add(builder.toString());
        lines.removeIf(String::isBlank);
        return lines;
    }
}