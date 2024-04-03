package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.meti.JavaLang.STATIC_KEYWORD;
import static com.meti.Lang.*;
import static com.meti.MagmaLang.*;

public class JavaToMagmaCompiler {
    public static String renderMagmaDeclaration(String name, String type) {
        return name + " : " + type;
    }

    public static String renderJavaDeclaration(String name, String type) {
        return type + " " + name;
    }

    static String run(String input) throws CompileException {
        var lines = split(input);

        var builder = new StringBuilder();
        var hasSeenPackage = false;
        for (String line : lines) {
            var state = compileRootStatement(line);
            builder.append(state.result);

            if (state.wasPackage) {
                if (hasSeenPackage) {
                    throw new CompileException("Only one package statement is allowed.");
                } else {
                    hasSeenPackage = true;
                }
            }
        }

        return builder.toString();
    }

    private static List<String> split(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);

            if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }

        lines.add(builder.toString());

        return lines.stream()
                .filter(line -> !line.isBlank())
                .toList();
    }

    private static State compileRootStatement(String input) throws CompileException {
        return streamCompilers(input)
                .map(Supplier::get)
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(() -> new CompileException("Unknown input: " + input));
    }

    private static Stream<Supplier<Optional<State>>> streamCompilers(String input) {
        return Stream.of(
                () -> compileClass(input),
                () -> compileImport(input),
                () -> compilePackage(input)
        );
    }

    private static Optional<State> compileClass(String input)  {
        var index = input.indexOf(CLASS_KEYWORD);
        if (index == -1) return Optional.empty();

        var isPublic = input.startsWith("public ");

        var contentStart = input.indexOf('{');
        if (contentStart == -1) return Optional.empty();

        var contentEnd = input.lastIndexOf('}');
        if (contentEnd == -1) return Optional.empty();

        var strip = input.substring(index + CLASS_KEYWORD.length(), contentStart).strip();

        String name;
        Optional<String> superclass;
        var extendsIndex = strip.indexOf("extends ");
        if (extendsIndex != -1) {
            name = strip.substring(0, extendsIndex).strip();
            superclass = Optional.of(strip.substring("extends ".length() + extendsIndex).strip());
        } else {
            name = strip;
            superclass = Optional.empty();
        }

        var content = input.substring(contentStart + 1, contentEnd).strip();
        String parameterString;
        if (content.isEmpty()) {
            parameterString = "";
        } else {
            var nameIndex = content.indexOf(name);
            if (nameIndex != -1) {
                var paramStart = content.indexOf('(');
                var paramEnd = content.indexOf(')');
                var paramString = content.substring(paramStart + 1, paramEnd).strip();
                var separator = paramString.indexOf(' ');
                if (separator == -1) {
                    parameterString = "";
                } else {
                    var inputParameterType = paramString.substring(0, separator).strip();
                    String outputParameterType;
                    if (inputParameterType.equals("int")) {
                        outputParameterType = "I32";
                    } else if(inputParameterType.equals("String")) {
                        outputParameterType = "String";
                    } else {
                        return Optional.empty();
                    }

                    var paramName = paramString.substring(separator + 1).strip();

                    parameterString = renderMagmaDeclaration(paramName, outputParameterType);
                }
            } else {
                parameterString = "";
            }
        }

        var exportString = isPublic ? EXPORT_KEYWORD : "";
        var membersString = superclass.map(value -> "implements " + value + ";").orElse("");
        var rendered = renderMagmaFunction(exportString, name, parameterString, renderContent(membersString));
        return Optional.of(new State(rendered));
    }

    private static Optional<State> compilePackage(String input) {
        if (!input.startsWith(JavaLang.PACKAGE_KEYWORD)) return Optional.empty();

        return Optional.of(new State("", true));
    }

    private static Optional<State> compileImport(String input) {
        if (!input.startsWith(IMPORT_KEYWORD)) return Optional.empty();

        var isStatic = input.startsWith(IMPORT_KEYWORD + STATIC_KEYWORD);

        var separator = input.lastIndexOf('.');
        var parentStart = isStatic
                ? IMPORT_KEYWORD.length() + STATIC_KEYWORD.length()
                : IMPORT_KEYWORD.length();

        var parent = input.substring(parentStart, separator);

        var child = input.substring(separator + 1).strip();
        var childString = child.equals("*")
                ? "*"
                : renderImportChildString(child);

        var rendered = renderImportWithChildString(parent, childString);
        return Optional.of(new State(rendered));
    }

    record State(String result, boolean wasPackage) {
        public State(String result) {
            this(result, false);
        }
    }
}
