package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {
    public static final List<String> methods = new ArrayList<>();

    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compileRoot(String input) {
        return compileAll(input, Main::compileRootSegment) + String.join("", methods);
    }

    private static String compileAll(String input, Function<String, String> compiler) {
        var segments = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;
        var i = 0;
        while (i < input.length()) {
            var c = input.charAt(i);

            if (c == '\'') {
                buffer.append(c);
                i++;

                var maybeSlash = input.charAt(i);
                buffer.append(maybeSlash);
                i++;

                if (maybeSlash == '\\') {
                    var escaped = input.charAt(i);
                    buffer.append(escaped);
                    i++;
                }

                var slash = input.charAt(i);
                buffer.append(slash);
                i++;
                continue;
            }

            buffer.append(c);
            i++;
            if (c == ';' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            }
            else if (c == '}' && depth == 1) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
                depth--;
            }
            else {
                if (c == '{') {
                    depth++;
                }
                if (c == '}') {
                    depth--;
                }
            }
        }
        segments.add(buffer.toString());

        var output = new StringBuilder();
        for (var segment : segments) {
            output.append(compiler.apply(segment));
        }

        return output.toString();
    }

    private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        var classIndex = stripped.indexOf("class ");
        if (classIndex >= 0) {
            var beforeClass = stripped.substring(0, classIndex);
            var afterClass = stripped.substring(classIndex + "class ".length());
            var contentStart = afterClass.indexOf("{");
            if (contentStart >= 0) {
                var name = afterClass.substring(0, contentStart).strip();
                var withEnd = afterClass.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    var content = withEnd.substring(0, withEnd.length() - "}".length());
                    return generatePlaceholder(beforeClass) + "struct " + name + " {" + compileAll(content, Main::compileClassSegment) + "\n};\n";
                }
            }
        }

        return generatePlaceholder(stripped);
    }

    private static String compileClassSegment(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        var paramStart = stripped.indexOf("(");
        if (paramStart >= 0) {
            var definition = stripped.substring(0, paramStart);
            var afterParams = stripped.substring(paramStart + "(".length());
            var paramEnd = afterParams.indexOf(")");
            if (paramEnd >= 0) {
                var params = afterParams.substring(0, paramEnd);
                var withoutParams = afterParams.substring(paramEnd + ")".length());

                var generated = compileDefinition(definition) + "(" + generatePlaceholder(params) + ")" + generatePlaceholder(withoutParams.strip()) + "\n";
                methods.add(generated);
                return "";
            }
        }

        return generatePlaceholder(stripped);
    }

    private static String compileDefinition(String input) {
        var stripped = input.strip();
        var nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            var beforeName = stripped.substring(0, nameSeparator);
            var name = stripped.substring(nameSeparator + " ".length());
            if (isSymbol(name)) {
                var typeSeparator = beforeName.lastIndexOf(" ");
                if (typeSeparator >= 0) {
                    var beforeType = beforeName.substring(0, typeSeparator);
                    var type = beforeName.substring(typeSeparator + " ".length());
                    return generatePlaceholder(beforeType) + " " + compileType(type) + " " + name;
                }
            }
        }

        return generatePlaceholder(stripped);
    }

    private static String compileType(String input) {
        var stripped = input.strip();
        if (stripped.equals("void")) {
            return "void";
        }

        return generatePlaceholder(stripped);
    }

    private static boolean isSymbol(String input) {
        var stripped = input.strip();
        for (var i = 0; i < stripped.length(); i++) {
            var c = stripped.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
}
