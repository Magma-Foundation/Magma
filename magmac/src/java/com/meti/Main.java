package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        try {
            compile("java", "mgs");
            compile("mgs", "js");
            compile("mgs", "d.ts");
            compile("mgs", "c");
            compile("mgs", "h");
        } catch (IOException | CompileException e) {
            throw new RuntimeException(e);
        }
    }

    private static void compile(String sourceEnv, String targetEnv) throws IOException, CompileException {
        var source = Paths.get(".", "magmac", "src", "java", "com", "meti", "Main." + sourceEnv);
        var input = Files.readString(source);
        var target = source.resolveSibling("Main." + targetEnv);
        Files.writeString(target, compile(input));
    }

    private static String compile(String input) throws CompileException {
        var lines = Strings.split(input);

        var output = new StringBuilder();
        for (String line : lines) {
            output.append(compileRoot(line));
        }

        return output.toString();
    }

    private static String compileRoot(String line) throws CompileException {
        var stripped = line.strip();

        if (stripped.isEmpty() || stripped.startsWith("package ")) return "";
        return compileImport(stripped)
                .or(() -> compileClass(stripped))
                .orElseGet(() -> new Err<>(new CompileException(line)))
                .$();
    }

    private static Optional<Result<String, CompileException>> compileClass(String input) {
        var classIndex = input.indexOf("class");
        if (classIndex == -1) return Optional.empty();

        var contentStart = input.indexOf('{');
        if (contentStart == -1) return Optional.empty();

        var name = input.substring(classIndex + "class".length(), contentStart).strip();
        var modifierString = input.startsWith("public ") ? "export " : "";

        var contentEnd = input.lastIndexOf('}');
        if (contentEnd == -1) return Optional.empty();

        var content = input.substring(contentStart + 1, contentEnd).strip();
        var inputContent = Strings.split(content);

        return compileClassMembers(inputContent)
                .mapErr(err -> new CompileException("Failed to compile class body: " + input, err))
                .mapValue(output -> Optional.of(renderClass(modifierString, name, output)))
                .into(Results::unwrapOptional);

    }

    private static String renderClass(String modifierString, String name, ClassMemberResult members) {
        var instanceContent = renderInstanceClassContent(modifierString, name, members);
        var staticContent = renderStaticClassContent(modifierString, name, members);
        return instanceContent + staticContent;
    }

    private static String renderInstanceClassContent(String modifierString, String name, ClassMemberResult members) {
        var joinedInstance = String.join("", members.instanceMembers);
        return modifierString + "class def " + name + "(){\n" + joinedInstance + "}\n";
    }

    private static String renderStaticClassContent(String modifierString, String name, ClassMemberResult members) {
        if (members.staticMembers.isEmpty()) return "";

        var joinedStatic = String.join("", members.staticMembers);
        return modifierString + "object " + name + " {\n" + joinedStatic + "}";
    }

    private static Result<ClassMemberResult, CompileException> compileClassMembers(List<String> inputContent) {
        var instanceContent = new ArrayList<String>();
        var staticContent = new ArrayList<String>();

        for (var input : inputContent) {
            if (input.isBlank()) continue;

            try {
                var result = compileClassMember(input);
                instanceContent.addAll(result.instanceMembers);
                staticContent.addAll(result.staticMembers);
            } catch (CompileException e) {
                return new Err<>(e);
            }
        }

        return new Ok<>(new ClassMemberResult(instanceContent, staticContent));
    }

    private static ClassMemberResult compileClassMember(String input) throws CompileException {
        return compileMethod(input).orElseThrow(() -> new CompileException("Unknown class member: " + input));
    }

    private static Optional<ClassMemberResult> compileMethod(String input) {
        var stripped = input.strip();

        var paramStart = stripped.indexOf('(');
        if (paramStart == -1) return Optional.empty();

        var paramEnd = stripped.indexOf(')');
        if (paramEnd == -1) return Optional.empty();

        var paramString = stripped.substring(paramStart + 1, paramEnd);
        var paramStrings = List.of(paramString.split(","));
        var renderedParams = compileMethodParams(paramStrings);

        var before = stripped.substring(0, paramStart).strip();
        var separator = before.lastIndexOf(' ');

        var modifiersAndTypeString = before.substring(0, separator).strip();
        var name = before.substring(separator + 1).strip();

        var modifiersAndType = Strings.splitTypeString(modifiersAndTypeString);
        if (modifiersAndType.isEmpty()) return Optional.empty();

        var modifiers = modifiersAndType.subList(0, modifiersAndType.size() - 1);
        var type = modifiersAndType.get(modifiersAndType.size() - 1);

        var modifierString = modifiers.contains("private") ? "private " : "";
        var rendered = "\t" +
                       modifierString +
                       "def " + name + "(" + renderedParams + ")" +
                       ": " + type +
                       " => {\n\t}\n";

        var state = modifiers.contains("static")
                ? new ClassMemberResult(Collections.emptyList(), Collections.singletonList(rendered))
                : new ClassMemberResult(Collections.singletonList(rendered), Collections.emptyList());

        return Optional.of(state);
    }

    private static String compileMethodParams(List<String> paramStrings) {
        var outputParams = Optional.<StringBuilder>empty();
        for (String string : paramStrings) {
            if (string.isBlank()) continue;

            var strippedParam = string.strip();
            var separator = strippedParam.lastIndexOf(' ');
            var type = strippedParam.substring(0, separator);
            var name = strippedParam.substring(separator + 1);

            var next = name + " : " + type;
            outputParams = Optional.of(outputParams.map(value -> value.append(", ").append(next))
                    .orElse(new StringBuilder(next)));
        }

        return outputParams.orElse(new StringBuilder()).toString();
    }

    private static Optional<Result<String, CompileException>> compileImport(String stripped) {
        if (!stripped.startsWith("import ")) return Optional.empty();

        var segments = stripped.substring("import ".length());
        var separator = segments.lastIndexOf('.');
        if (separator == -1) return Optional.empty();

        var parent = segments.substring(0, separator);
        var child = segments.substring(separator + 1);
        return Optional.of(new Ok<>("import { " + child + " } from " + parent + ";\n"));
    }

    record ClassMemberResult(List<String> instanceMembers, List<String> staticMembers) {
    }
}
