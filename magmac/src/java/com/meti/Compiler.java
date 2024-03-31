package com.meti;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Compiler {
    public static final String IMPORT_KEYWORD = "import ";
    public static final String STATIC_KEYWORD = "static ";
    public static final String IMPORT_STATIC = IMPORT_KEYWORD + STATIC_KEYWORD;
    public static final String PUBLIC_KEYWORD = "public ";
    public static final String CLASS_KEYWORD = "class ";
    public static final String I64 = "I64";
    public static final String I32 = "I32";
    public static final String LONG = "long";
    public static final String INT = "int";
    public static final String CONST_KEYWORD = "const ";
    public static final String LET_KEYWORD = "let ";
    public static final String FINAL_KEYWORD = "final";
    public static final String LOWER_VOID = "void";
    public static final String CAMEL_VOID = "Void";
    public static final String EXPORT_KEYWORD = "export ";
    public static final String INTERFACE_KEYWORD = "interface ";

    static String compile(String input) {
        var args = split(input);

        var imports = new ArrayList<String>();
        var objects = new ArrayList<String>();
        var classes = new ArrayList<String>();

        for (String arg : args) {
            var state = compileRootStatement(arg.strip());
            state.importValue.ifPresent(imports::add);
            state.instanceValue.ifPresent(classes::add);
            state.staticValue.ifPresent(objects::add);
        }

        var importString = String.join("\n", imports).strip();
        var importStream = importString.isEmpty() ? Stream.<String>empty() : Stream.of(importString);

        return Stream.concat(importStream, Stream.of(objects, classes).filter(list -> !list.isEmpty()).map(list -> String.join("", list).strip())).collect(Collectors.joining("\n\n"));
    }

    private static List<String> split(String input) {
        var state = new SplittingState();

        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && state.isLevel()) {
                state.advance();
            } else if (c == '}' && state.isShallow()) {
                state.append('}');
                state.descend();
                state.advance();
            } else {
                if (c == '{' || c == '(') state.ascend();
                if (c == '}' || c == ')') state.descend();
                state.append(c);
            }
        }

        state.advance();
        state.clean();
        return state.unwrap();
    }

    private static State compileRootStatement(String input) {
        return compileImport(input)
                .or(() -> compileClass(input))
                .or(() -> compileInterface(input))
                .orElse(new State(Optional.empty(), Optional.empty(), Optional.empty()));
    }

    private static Optional<State> compileInterface(String input) {
        var index = input.indexOf(INTERFACE_KEYWORD);
        if (index != -1) {
            var isPublic = input.startsWith(PUBLIC_KEYWORD);

            var contentStart = input.indexOf('{');
            if (contentStart == -1) return Optional.empty();

            var contentEnd = input.lastIndexOf('}');
            if (contentEnd == -1) return Optional.empty();

            var content = input.substring(contentStart, contentEnd + 1);

            var name = input.substring(index + INTERFACE_KEYWORD.length(), contentStart).strip();
            var rendered = renderMagmaTrait(isPublic ? EXPORT_KEYWORD : "", name, content);

            return Optional.of(new State(Optional.of(rendered), Optional.empty(), Optional.empty()));
        }
        return Optional.empty();
    }

    static String renderMagmaTrait(String name) {
        return renderMagmaTrait("", name, "{}");
    }

    static String renderMagmaTrait(String prefixString, String name, String content) {
        return prefixString + "trait " + name + " " + content;
    }

    static String renderJavaInterface(String name) {
        return renderJavaInterface("", name);
    }

    static String renderJavaInterface(String prefixString, String name) {
        return prefixString + INTERFACE_KEYWORD + name + " {}";
    }

    private static Optional<State> compileClass(String input) {
        if (!input.contains(CLASS_KEYWORD)) return Optional.empty();
        var isPublic = input.startsWith(PUBLIC_KEYWORD);
        var nameStart = input.indexOf(CLASS_KEYWORD) + CLASS_KEYWORD.length();

        var bodyStart = input.indexOf('{');
        var bodyEnd = input.lastIndexOf('}');
        if (bodyEnd == -1) return Optional.empty();

        var name = input.substring(nameStart, bodyStart).strip();
        var inputContent = input.substring(bodyStart + 1, bodyEnd);
        var splitContent = split(inputContent);

        var outputValues = new ArrayList<String>();
        var outputMore = new ArrayList<String>();

        for (var classMember : splitContent) {
            var compiledClassMember = compileMethod(classMember)
                    .or(() -> compileDefinition(classMember))
                    .orElse(new State(Optional.empty(), Optional.of(classMember), Optional.empty()));

            compiledClassMember.instanceValue.ifPresent(outputValues::add);
            compiledClassMember.staticValue.ifPresent(outputMore::add);
        }

        var value = String.join("", outputValues);
        var flagString = isPublic ? EXPORT_KEYWORD : "";

        var renderedClass = renderMagmaClass(flagString, name, value);

        Optional<String> renderedMore;
        if (outputMore.isEmpty()) {
            renderedMore = Optional.empty();
        } else {
            String content = String.join("", outputMore);
            renderedMore = Optional.of(renderObject(flagString, name, content));
        }

        return Optional.of(new State(Optional.empty(), Optional.of(renderedClass), renderedMore));
    }

    private static Optional<State> compileMethod(String classMember) {
        var lines = Arrays.stream(classMember.split("\n")).toList();

        var annotations = lines.stream()
                .map(String::strip)
                .filter(line -> line.startsWith("@"))
                .map(line -> line.substring(1))
                .map(line -> compileAnnotation(line).orElse(line))
                .toList();

        var methodString = lines.stream()
                .filter(line -> !line.strip().startsWith("@"))
                .collect(Collectors.joining("\n"));

        var paramStart = methodString.indexOf('(');
        if (paramStart == -1) return Optional.empty();

        var before = methodString.substring(0, paramStart).strip();
        var separator = before.lastIndexOf(' ');
        if (separator == -1) return Optional.empty();

        var name = before.substring(separator + 1).strip();
        var flagsAndType = before.substring(0, separator).strip();
        var typeSeparator = flagsAndType.lastIndexOf(' ');

        List<String> inputFlags;
        String outputType;
        if (typeSeparator == -1) {
            inputFlags = Collections.emptyList();
            outputType = compileType(flagsAndType);
        } else {
            inputFlags = Arrays.stream(flagsAndType.substring(0, typeSeparator).split(" ")).toList();
            outputType = compileType(flagsAndType.substring(typeSeparator + 1).strip());
        }

        var annotationsString = annotations.stream()
                .map(Compiler::renderAnnotation)
                .collect(Collectors.joining());

        var paramEnd = methodString.indexOf(')');
        if (paramEnd == -1) return Optional.empty();

        var contentStart = methodString.indexOf('{');
        if (contentStart == -1) return Optional.empty();

        if (!(paramEnd < contentStart)) return Optional.empty();
        var throwsString = methodString.substring(paramEnd + 1, contentStart).strip();
        String result;

        var contentEnd = methodString.lastIndexOf('}');
        if (contentEnd == -1) return Optional.empty();

        var content = methodString.substring(contentStart, contentEnd + 1).strip();
        if (throwsString.isEmpty()) {
            result = renderMagmaMethodWithType(annotationsString, name, outputType, content, "");
        } else {
            var exceptionName = throwsString.substring("throws ".length()).strip();
            result = renderMagmaMethodWithException(annotationsString, name, outputType, content, exceptionName);
        }

        return inputFlags.contains("static")
                ? Optional.of(new State(Optional.empty(), Optional.empty(), Optional.of(result)))
                : Optional.of(new State(Optional.empty(), Optional.of(result), Optional.empty()));
    }

    private static Optional<String> compileAnnotation(String line) {
        var paramStart = line.indexOf('(');
        var paramEnd = line.lastIndexOf(')');

        if (paramStart == -1 || paramEnd == -1) {
            return Optional.empty();
        } else {
            var annotationName = line.substring(0, paramStart).strip();
            var arg = line.substring(paramStart + 1, paramEnd)
                    .strip();

            var separator = arg.indexOf('=');
            var name = arg.substring(0, separator).strip();
            var right = arg.substring(separator + 1).strip();

            var contentStart = right.indexOf('{');
            var contentEnd = right.lastIndexOf('}');

            return Optional.of(annotationName + "(" + name + " = [" + right.substring(contentStart + 1, contentEnd) + "])");
        }
    }

    static String renderMagmaMethodWithException(String annotationsString, String name, String type, String content, String exceptionName) {
        return renderMagmaMethodWithType(annotationsString, name, type, content, " ? " + exceptionName);
    }

    private static Optional<State> compileDefinition(String inputContent) {
        var valueSeparator = inputContent.indexOf('=');
        if (valueSeparator == -1) return Optional.empty();

        var beforeSlice = inputContent.substring(0, valueSeparator).strip();
        var nameSeparator = beforeSlice.lastIndexOf(' ');
        if (nameSeparator == -1) return Optional.empty();

        var flagsAndType = beforeSlice.substring(0, nameSeparator);
        var typeSeparator = flagsAndType.lastIndexOf(' ');

        List<String> flags;
        String inputType;
        if (typeSeparator == -1) {
            flags = Collections.emptyList();
            inputType = flagsAndType;
        } else {
            flags = List.of(flagsAndType.substring(0, typeSeparator).split(" "));
            inputType = flagsAndType.substring(typeSeparator + 1).strip();
        }

        var outputType = compileType(inputType);

        var name = beforeSlice.substring(nameSeparator + 1);
        var value = inputContent.substring(valueSeparator + 1).strip();

        var mutabilityString = flags.contains(FINAL_KEYWORD) ? CONST_KEYWORD : LET_KEYWORD;
        var flagString = flags.contains("public") ? "pub " : "";
        var rendered = renderMagmaDefinition(flagString, mutabilityString, name, outputType, value);

        return Optional.of(flags.contains("static") ? new State(Optional.empty(), Optional.empty(), Optional.of(rendered)) : new State(Optional.empty(), Optional.of(rendered), Optional.empty()));
    }

    private static String compileType(String inputType) {
        return switch (inputType) {
            case LONG -> I64;
            case INT -> I32;
            case LOWER_VOID -> CAMEL_VOID;
            default -> inputType;
        };
    }

    static String renderObject(String name, String content) {
        return renderObject("", name, content);
    }

    static String renderObject(String flagString, String name, String content) {
        return flagString + "object " + name + " = {" + content + "\n}";
    }

    private static Optional<State> compileImport(String input) {
        if (!input.startsWith(IMPORT_KEYWORD)) return Optional.empty();
        var isStatic = input.startsWith(IMPORT_STATIC);
        var importKeyword = isStatic ? IMPORT_STATIC : IMPORT_KEYWORD;
        var segmentsString = input.substring(importKeyword.length());

        var separator = segmentsString.lastIndexOf('.');
        var parent = segmentsString.substring(0, separator);
        var child = segmentsString.substring(separator + 1);

        return Optional.of(new State(Optional.of(child.equals("*") ? renderMagmaImportForAllChildren(parent) : renderMagmaImport(parent, child)), Optional.empty(), Optional.empty()));
    }

    static String renderMagmaImport(String parent, String child) {
        return renderMagmaImportWithChildString(parent, "{ " + child + " } from ");
    }

    static String renderMagmaImportForAllChildren(String parent) {
        return renderMagmaImportWithChildString(parent, "");
    }

    private static String renderMagmaImportWithChildString(String parent, String childString) {
        return "extern " + IMPORT_KEYWORD + childString + parent + ";";
    }

    static String renderMagmaClass(String name, String content) {
        return renderMagmaClass("", name, content);
    }

    static String renderMagmaClass(String prefix, String name, String content) {
        return prefix + CLASS_KEYWORD + "def " + name + "() " + "=> {" + content + "\n}";
    }

    static String renderMagmaMethodWithType(String prefix, String name, String type, String content) {
        return renderMagmaMethodWithType(prefix, name, type, content, "");
    }

    static String renderMagmaMethodWithType(String prefix, String name, String type, String content, String exceptionString) {
        return prefix + "\n\tdef " + name + "() : " + type + exceptionString + " => " + content;
    }

    static String renderJavaClass(String prefix, String name, String content) {
        return prefix + CLASS_KEYWORD + name + " {" + content + "}";
    }

    static String renderMagmaDefinition(String name, String type) {
        return renderMagmaDefinition(name, type, "0");
    }

    static String renderMagmaDefinition(String name, String type, String value) {
        return renderMagmaDefinition("", LET_KEYWORD, name, type, value);
    }

    static String renderMagmaDefinition(String flagString, String mutabilityString, String name, String type, String value) {
        return "\n\t" + flagString + mutabilityString + name + " : " + type + " = " + value + ";";
    }

    static String renderAnnotation(String name) {
        return renderAnnotation(name, "");
    }

    static String renderAnnotation(String name, String valueString) {
        return "\n\t@" + name + valueString;
    }

    record State(Optional<String> importValue, Optional<String> instanceValue, Optional<String> staticValue) {
    }
}