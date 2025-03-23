package magma;

import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static final Path SOURCE_DIRECTORY = Paths.get(".", "src", "java");

    public static void main(String[] args) {
        try (Stream<Path> paths = Files.walk(SOURCE_DIRECTORY)) {
            Set<Path> sources = paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toSet());

            for (Path source : sources) {
                Path relative = SOURCE_DIRECTORY.relativize(source);
                Path parent = relative.getParent();
                if (parent.startsWith(Paths.get("magma", "java"))) continue;

                String input = Files.readString(source);
                String output = compile(input);

                String nameWithExt = relative.getFileName().toString();
                String name = nameWithExt.substring(0, nameWithExt.lastIndexOf('.'));

                Path targetParent = Paths.get(".", "src", "windows").resolve(parent);
                if (!Files.exists(targetParent)) Files.createDirectories(targetParent);

                Path target = targetParent.resolve(name + ".c");
                Files.writeString(target, output);
            }
        } catch (CompileException | IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) throws CompileException {
        ArrayList<String> segments = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        int depth = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            buffer.append(c);
            if (c == ';' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
            }
        }
        segments.add(buffer.toString());

        Result<StringBuilder, CompileException> output = new Ok<>(new StringBuilder());
        for (String segment : segments) {
            output = output
                    .and(() -> compileRootSegment(segment))
                    .map(tuple -> tuple.left().append(tuple.right()));
        }

        return output.toString();
    }

    private static Result<String, CompileException> compileRootSegment(String input) {
        if (input.startsWith("package ")) return generateWhitespace();
        if (input.strip().startsWith("import ")) {
            String right = input.strip().substring("import ".length());
            if (right.endsWith(";")) {
                String content = right.substring(0, right.length() - ";".length());
                List<String> segments = Arrays.asList(content.split(Pattern.quote(".")));
                if (segments.size() >= 3 && segments.subList(0, 3).equals(List.of("java", "util", "function")))
                    return generateWhitespace();

                String joined = String.join("/", segments);
                return new Ok<>("#include <" + joined + ".h>\n");
            }
        }

        Result<String, CompileException> maybeClass = compileClass(input);
        if (maybeClass.isOk()) return maybeClass;

        int interfaceIndex = input.indexOf("interface ");
        if (interfaceIndex >= 0) {
            String right = input.substring(interfaceIndex + "interface ".length());
            int contentStart = right.indexOf("{");
            if (contentStart >= 0) {
                String beforeContent = right.substring(0, contentStart).strip();
                if (beforeContent.endsWith(">")) {
                    if (beforeContent.contains("<")) {
                        return generateWhitespace();
                    }
                }

                return generateStruct("Temp");
            }
        }

        int recordKeyword = input.indexOf("record ");
        if (recordKeyword >= 0) {
            String right = input.substring(recordKeyword + "record ".length());
            int contentStart = right.indexOf("{");
            if (contentStart >= 0) {
                String beforeContent = right.substring(0, contentStart).strip();
                if (beforeContent.endsWith(">")) {
                    if (beforeContent.contains("<")) {
                        return generateWhitespace();
                    }
                }
                return generateStruct(beforeContent);
            }
        }

        return new Err<>(new CompileException("Invalid root segment", input));
    }

    private static Result<String, CompileException> generateStruct(String name) {
        return new Ok<>("struct " + name + " {\n};\n");
    }

    private static Result<String, CompileException> generateWhitespace() {
        return new Ok<>("");
    }

    private static Result<String, CompileException> compileClass(String input) {
        int classIndex = input.indexOf("class ");
        if (classIndex < 0) return createInfixError(input, "class ");

        String right = input.substring(classIndex + "class ".length());
        int contentStart = right.indexOf("{");
        if (contentStart < 0) return createInfixError(right, "{");

        String beforeContent = right.substring(0, contentStart).strip();

        String name;
        int implementsIndex = beforeContent.indexOf(" implements ");
        if (implementsIndex < 0) {
            name = beforeContent;
        } else {
            String beforeImplements = beforeContent.substring(0, implementsIndex).strip();
            if (!beforeImplements.endsWith(">")) {
                name = beforeImplements;
            } else {
                String withoutEnd = beforeImplements.substring(0, beforeImplements.length() - ">".length());
                int typeParamStart = withoutEnd.indexOf("<");
                if (typeParamStart < 0) {
                    name = beforeImplements;
                } else {
                    return new Ok<>("");
                }
            }
        }

        return generateStruct(name);
    }

    private static Err<String, CompileException> createInfixError(String input, String infix) {
        return new Err<>(new CompileException("Infix '" + infix + "' not present", input));
    }

}
