#include "Compiler.c"public record Compiler(String input, String sourceExtension, String targetExtension) {
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

    private static Optional<MethodState> compileMethod(String slice, String targetExtension) {
        var paramStart = slice.indexOf('(');
        if (paramStart == -1) return Optional.empty();

        var paramEnd = slice.indexOf(')');
        if (paramEnd == -1) return Optional.empty();

        var bodyStart = slice.indexOf('{');
        if (bodyStart == -1) return Optional.empty();

        var bodyEnd = slice.lastIndexOf('}');
        if (bodyEnd == -1) return Optional.empty();

        var functionBody = slice.substring(bodyStart, bodyEnd + 1).strip();

        if (targetExtension.equals(".js")) {
            var indicator = slice.indexOf("def");
            if(indicator == -1) return Optional.empty();

            var start = indicator + "def".length();
            var endIndex = slice.indexOf('(');
            if (!(start < endIndex)) return Optional.empty();

            var name = slice.substring(start, endIndex).strip();
            var flags = splitFlags(slice.substring(0, indicator).strip());

            Optional<String> export_;
            if (flags.contains("pub")) {
                export_ = Optional.of(name);
            } else {
                export_ = Optional.empty();
            }

            return Optional.of(new MethodState("\n\tfunction " + name + "()" + functionBody, export_));
        } else if (targetExtension.equals(".mgs")) {
            var paramStrings = Arrays.stream(slice.substring(paramStart + 1, paramEnd).split(","))
                    .map(Compiler::compileJavaParameter)
                    .flatMap(Optional::stream)
                    .collect(Collectors.joining(", "));

            var before = slice.substring(0, paramStart);
            var nameSeparator = before.lastIndexOf(' ');
            if (nameSeparator == -1) return Optional.empty();

            var name = before.substring(nameSeparator + 1).strip();

            var keyString = before.substring(0, nameSeparator).strip();
            var typeSeparator = keyString.lastIndexOf(' ');
            if (typeSeparator == -1) return Optional.empty();

            var flags = splitFlags(keyString.substring(0, typeSeparator));
            var inputType = keyString.substring(typeSeparator + 1).strip();
            var outputType = compileType(inputType);

            var flagsString = flags.contains("public") ? "pub " : "";
            return Optional.of(new MethodState("\n\t" + flagsString + "def " + name + "(" + paramStrings + ") : " + outputType + " => " + functionBody, Optional.empty()));
        } else {
            return Optional.empty();
        }
    }

    private static List<String> splitFlags(String slice) {
        return Arrays.asList(slice.strip().split(" "));
    }

    private static Optional<String> compileJavaParameter(String line) {
        var separator = line.lastIndexOf(' ');
        if (separator == -1) return Optional.empty();

        var typeString = line.substring(0, separator).strip();
        boolean isVarArgs;
        String actualType;
        if (typeString.endsWith("...")) {
            isVarArgs = true;
            actualType = typeString.substring(0, typeString.length() - "...".length());
        } else {
            isVarArgs = false;
            actualType = typeString;
        }

        var nameString = line.substring(separator + 1).strip();
        var prefix = isVarArgs ? "..." : "";

        return Optional.of(prefix + nameString + " : " + compileType(actualType));
    }

    private static String compileType(String inputType) {
        String outputType;
        if (inputType.equals("void")) {
            outputType = "Void";
        } else {
            outputType = inputType;
        }
        return outputType;
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

        var body = line.substring(line.indexOf('{') + 1, line.lastIndexOf('}'));
        var bodyString = split(body);

        List<String> exports = new ArrayList<>();
        StringBuilder newBody = new StringBuilder();
        for (String s : bodyString) {
            var state = compileMethod(s, targetExtension).orElse(new MethodState(s, Optional.empty()));
            newBody.append(state.value);

            state.export.ifPresent(exports::add);
        }
        var bodyString1 = newBody.toString();

        if (line.startsWith("export class def ")) {
            var paramStart = line.indexOf('(');
            var name = line.substring("export class def ".length(), paramStart).strip();

            if (targetExtension().equals(".js")) {
                var exportsString = String.join("", exports);
                return new Application.State("\nfunction " + name + "(){" + bodyString1 + "\n\treturn {" + exportsString + "};\n}", Optional.of(name));
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
        } else if (line.startsWith("public class ")) {
            var name = line.substring("public class ".length(), line.indexOf('{')).strip();
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
            } else if (c == '}' && depth == 1 && !builder.toString().strip().startsWith("import ")) {
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

    record MethodState(String value, Optional<String> export) {

    }
}