package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input) + "int main(){\n\t__main__();\n\treturn 0;\n}");

            Process process = new ProcessBuilder("cmd.exe", "/c", "build.bat")
                    .directory(Paths.get(".").toFile())
                    .inheritIO()
                    .start();

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        return getString(input, Main::compileRootSegment);
    }

    private static String getString(String input, Function<String, String> compiler) {
        return compileAll(input, Main::divideStatements, compiler, Main::mergeStatements);
    }

    private static String compileAll(
            String input,
            Function<String, ArrayList<String>> divider,
            Function<String, String> compiler,
            BiFunction<StringBuilder, String, StringBuilder> merger
    ) {
        ArrayList<String> segments = divider.apply(input);

        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            String compiled = compiler.apply(segment);
            output = merger.apply(output, compiled);
        }

        return output.toString();
    }

    private static StringBuilder mergeStatements(StringBuilder output, String compiled) {
        return output.append(compiled);
    }

    private static ArrayList<String> divideStatements(String input) {
        ArrayList<String> segments = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        int depth = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            buffer.append(c);
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
        return segments;
    }

    private static String compileRootSegment(String input) {
        if (input.startsWith("package ")) {
            return "";
        }

        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String afterKeyword = input.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                if (isSymbol(name)) {
                    String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                    if (withEnd.endsWith("}")) {
                        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                        String outputContent = getString(inputContent, Main::compileClassSegment);
                        return "struct " + name + " {\n};\n" + outputContent;
                    }
                }
            }
        }

        return generatePlaceholder(input) + "\n";
    }

    private static boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static String compileClassSegment(String input) {
        return compileMethod(input).orElseGet(() -> generatePlaceholder(input));

    }

    private static Optional<String> compileMethod(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return Optional.empty();
        }

        String inputDefinition = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + 1);

        return compileDefinition(inputDefinition).flatMap(outputDefinition -> {
            int paramEnd = withParams.indexOf(")");
            if (paramEnd >= 0) {
                String params = withParams.substring(0, paramEnd).strip();
                return Optional.of(outputDefinition + "(){" + "\n}\n");
            }
            else {
                return Optional.empty();
            }
        });
    }

    private static Optional<String> compileDefinition(String definition) {
        int nameSeparator = definition.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return Optional.empty();
        }
        String beforeName = definition.substring(0, nameSeparator).strip();
        String oldName = definition.substring(nameSeparator + " ".length()).strip();
        if (!isSymbol(oldName)) {
            return Optional.empty();
        }
        String newName = oldName.equals("main") ? "__main__" : oldName;

        int typeSeparator = beforeName.lastIndexOf(" ");
        if (typeSeparator >= 0) {
            String inputType = beforeName.substring(typeSeparator + " ".length()).strip();
            return compileType(inputType).map(outputType -> {
                return outputType + " " + newName;
            });
        }
        return Optional.empty();
    }

    private static Optional<String> compileType(String input) {
        String stripped = input.strip();
        if (stripped.equals("new")) {
            return Optional.empty();
        }

        if (stripped.equals("String")) {
            return Optional.of("char*");
        }

        return Optional.of(stripped);
    }

    private static String generatePlaceholder(String input) {
        String replaced = input.strip()
                .replace("/* ", "<cmt-start>")
                .replace(" */", "<cmt-end>");

        return "/* " + replaced + " */";
    }
}
