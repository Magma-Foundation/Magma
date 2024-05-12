package com.meti;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MagmaCompiler {
    static StackResult compileMagmaRoot(String line, String targetExtension) throws CompileException {
        return compileMagmaImport(line, targetExtension)
                .or(() -> compileMagmaFunction(line, targetExtension, 0, new LinkedList<>()))
                .orElseGet(() -> new Err<>(new CompileException(line)))
                .$();
    }

    static Optional<Result<StackResult, CompileException>> compileMagmaFunction(String line, String targetExtension, int indent, LinkedList<String> stack) {
        var stripped = line.strip();
        var functionKeyword = stripped.indexOf("def ");
        if (functionKeyword == -1) return Optional.empty();

        var isExported = stripped.startsWith("export ");
        var modifiers = List.of(stripped.substring(0, functionKeyword).strip().split(" "));
        var name = stripped.substring(functionKeyword + "def ".length(), stripped.indexOf('(')).strip();

        var contentStart = stripped.indexOf('{');
        var contentEnd = stripped.lastIndexOf('}');
        var content = stripped.substring(contentStart + 1, contentEnd);
        var inputContent = Splitter.split(content);
        var outputContent = new StringBuilder();

        var outerContent = new StringBuilder();
        for (String s : inputContent) {
            if (!s.isBlank()) {
                try {
                    stack.push(name);

                    var result = compileMagmaFunctionMember(targetExtension, s, 1, stack);
                    var inner = result.findInner().orElse("");
                    result.findOuter().ifPresent(outerContent::append);
                    outputContent.append(inner);
                } catch (CompileException e) {
                    return Optional.of(new Err<>(e));
                }
            }
        }

        var output = renderMagmaFunction(targetExtension, isExported, name, outputContent.toString(), modifiers.contains("class"), indent, stack)
                .withOuter(outerContent.toString());

        return Optional.of(new Ok<>(output));
    }

    private static StackResult compileMagmaFunctionMember(String targetExtension, String input, int indent, LinkedList<String> stack) throws CompileException {
        return compileMagmaFunction(input, targetExtension, indent, stack)
                .orElseGet(() -> new Err<>(new CompileException(input)))
                .$();
    }

    static StackResult renderMagmaFunction(String targetExtension, boolean isExported, String name, String content, boolean isClass, int indent, LinkedList<String> stack) {
        if (targetExtension.equals("js")) {
            var exportedString = isExported ? "module.exports = {\n\t" + name + "\n}\n" : "";
            var classString = isClass ? "\treturn {};\n" : "";

            return new InnerResult("\t".repeat(indent) + "function " + name + "(){\n" + content + classString + "\t".repeat(indent) + "}\n" + exportedString);
        } else if (targetExtension.equals("d.ts")) {
            return new InnerResult(isExported ? "export function " + name + "() : {};" : "");
        } else {
            return transformMagmaFunctionToC(targetExtension, name, isExported, content, isClass, stack);
        }
    }

    static StackResult transformMagmaFunctionToC(String targetExtension, String name, boolean isExported, String content, boolean isClass, LinkedList<String> stack) {
        if (isClass) {
            var structType = "struct " + name + "_t";
            var structString = structType + " {\n}\n";
            if (targetExtension.equals("c")) {
                var structString1 = isExported ? "" : structString;
                return new InnerResult(structString1 + renderCFunction(structType, name, "\t" + structType + " this;\n" +
                                                                                         content +
                                                                                         "\treturn this;\n"));
            } else {
                return new InnerResult(isExported ? structString + structType + " " + name + "();\n" : "");
            }
        } else {
            if (targetExtension.equals("c")) {
                var afterName = IntStream.range(0, stack.size())
                        .map(index -> stack.size() - index - 1)
                        .mapToObj(stack::get)
                        .map(value -> "_" + value)
                        .collect(Collectors.joining());

                return new OuterResult(renderCFunction("void", name + afterName, ""));
            } else {
                return new EmptyResult();
            }
        }
    }

    private static String renderCFunction(String structType, String name, String content) {
        return structType + " " + name + "(){\n" + content + "}\n";
    }

    static Optional<Result<StackResult, CompileException>> compileMagmaImport(String line, String targetExtension) {
        var stripped = line.strip();
        if (!stripped.startsWith("import ")) return Optional.empty();

        var childStart = stripped.indexOf('{');
        var childEnd = stripped.indexOf('}');
        var child = stripped.substring(childStart + 1, childEnd).strip();
        var parent = stripped.substring(stripped.indexOf("from") + "from".length()).strip();
        var output = renderMagmaImport(targetExtension, child, parent);
        return Optional.of(new Ok<>(new InnerResult(output)));
    }

    static String renderMagmaImport(String targetExtension, String child, String parent) {
        if (targetExtension.equals("js") || targetExtension.equals("d.ts")) {
            return "import { " + child + " } from \"" + parent + "\";\n";
        } else if (targetExtension.equals("h")) {
            return "#include <" + parent + "/" + child + ".h>\n";
        } else {
            return "";
        }
    }
}