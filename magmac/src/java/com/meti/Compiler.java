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

    static String compile(String input) {
        var args = split(input);
        var values = new ArrayList<String>();
        var more = new ArrayList<String>();

        for (String arg : args) {
            var state = compileRootStatement(arg.strip());
            state.value.ifPresent(values::add);
            state.more.ifPresent(more::add);
        }

        return Stream.of(values, more)
                .filter(list -> !list.isEmpty())
                .map(list -> String.join("", list))
                .collect(Collectors.joining("\n\n"));
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
                if (c == '{') state.ascend();
                if (c == '}') state.descend();
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
                .orElse(new State(Optional.empty(), Optional.empty()));
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
            var compiledClassMember = compileDefinition(classMember)
                    .or(() -> compileMethod(classMember))
                    .orElse(new State(Optional.of(classMember), Optional.empty()));

            compiledClassMember.value.ifPresent(outputValues::add);
            compiledClassMember.more.ifPresent(outputMore::add);
        }

        var value = String.join("", outputValues);
        var flagString = isPublic ? "export " : "";

        var renderedClass = renderMagmaClass(flagString, name, value);

        Optional<String> renderedMore;
        if (outputMore.isEmpty()) {
            renderedMore = Optional.empty();
        } else {
            String content = String.join("", outputMore);
            renderedMore = Optional.of(renderObject(flagString, name, content));
        }

        return Optional.of(new State(Optional.of(renderedClass), renderedMore));
    }

    private static Optional<? extends State> compileMethod(String classMember) {
        var paramStart = classMember.indexOf('(');
        if (paramStart == -1) return Optional.empty();

        var before = classMember.substring(0, paramStart).strip();
        var separator = before.lastIndexOf(' ');
        if (separator == -1) return Optional.empty();

        var name = before.substring(separator + 1).strip();
        var annotationsAndType = before.substring(0, separator).strip();
        var typeSeparator = annotationsAndType.lastIndexOf('\n');
        List<String> annotations;
        String type;
        if (typeSeparator == -1) {
            annotations = Collections.emptyList();
            type = compileType(annotationsAndType);
        } else {
            var asList = Arrays.stream(annotationsAndType.split("\n"))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .toList();

            annotations = asList.subList(0, asList.size() - 1).stream()
                    .map(element -> element.substring(1))
                    .collect(Collectors.toList());
            type = compileType(asList.get(asList.size() - 1));
        }

        var annotationsString = annotations.stream()
                .map(Compiler::renderAnnotation)
                .collect(Collectors.joining());

        var paramEnd = classMember.indexOf(')');
        if(paramEnd == -1) return Optional.empty();

        var contentStart = classMember.indexOf('{');
        if(contentStart == -1) return Optional.empty();

        var throwsString = classMember.substring(paramEnd + 1, contentStart).strip();
        String result;

        if(throwsString.isEmpty()) {
            result = renderMagmaMethodWithType(annotationsString, name, type, "{}", "");
        } else {
            var exceptionName = throwsString.substring("throws ".length()).strip();
            result = renderMagmaMethodWithException(annotationsString, name, type, "{}", exceptionName);
        }

        return Optional.of(new State(Optional.of(result), Optional.empty()));
    }

    static String renderMagmaMethodWithException(String annotationsString, String name, String type, String content, String exceptionName) {
        return renderMagmaMethodWithType(annotationsString, name, type, content, " ? " + exceptionName);
    }

    private static Optional<State> compileDefinition(String inputContent) {
        var valueSeparator = inputContent.indexOf('=');
        if (valueSeparator == -1) return Optional.empty();

        var beforeSlice = inputContent.substring(0, valueSeparator).strip();
        var nameSeparator = beforeSlice.lastIndexOf(' ');
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

        return Optional.of(flags.contains("static")
                ? new State(Optional.empty(), Optional.of(rendered))
                : new State(Optional.of(rendered), Optional.empty()));
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
        return flagString + "object " + name + " {" + content + "\n}";
    }

    private static Optional<State> compileImport(String input) {
        if (!input.startsWith(IMPORT_KEYWORD)) return Optional.empty();
        var isStatic = input.startsWith(IMPORT_STATIC);
        var importKeyword = isStatic ? IMPORT_STATIC : IMPORT_KEYWORD;
        var segmentsString = input.substring(importKeyword.length());

        var separator = segmentsString.lastIndexOf('.');
        var parent = segmentsString.substring(0, separator);
        var child = segmentsString.substring(separator + 1);

        return Optional.of(new State(Optional.of(child.equals("*")
                ? renderMagmaImportForAllChildren(parent)
                : renderMagmaImport(parent, child)), Optional.empty()));
    }

    static String renderMagmaImport(String parent, String child) {
        return renderMagmaImportWithChildString(parent, "{ " + child + " } from ");
    }

    static String renderMagmaImportForAllChildren(String parent) {
        return renderMagmaImportWithChildString(parent, "");
    }

    private static String renderMagmaImportWithChildString(String parent, String childString) {
        return "extern " + IMPORT_KEYWORD + childString + parent + ";\n";
    }

    static String renderMagmaClass(String name, String content) {
        return renderMagmaClass("", name, content);
    }

    static String renderExportedMagmaClass(String name, String content) {
        return renderMagmaClass("export ", name, content);
    }

    private static String renderMagmaClass(String prefix, String name, String content) {
        return renderMagmaMethod(prefix + CLASS_KEYWORD, name, content);
    }

    static String renderMagmaMethod(String prefix, String name, String content) {
        return renderMagmaMethod(prefix, name, "", content);
    }

    static String renderMagmaMethodWithType(String prefix, String name, String type, String content) {
        return renderMagmaMethodWithType(prefix, name, type, content, "");
    }

    static String renderMagmaMethodWithType(String prefix, String name, String type, String content, String exceptionString) {
        return prefix + "def " + name + "() : " + type + exceptionString + " => {" + content + "\n}";
    }

    static String renderMagmaMethod(String prefix, String name, String typeString, String content) {
        return prefix + "def " + name + "() " + typeString + "=> {" + content + "\n}";
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
        return "@" + name + "\n";
    }

    record State(Optional<String> value, Optional<String> more) {
    }
}