package com.meti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        var outputMore = new ArrayList<String >();

        for (var classMember : splitContent) {
            var compiledClassMember = compileDefinition(classMember)
                    .orElse(new State(Optional.of(classMember), Optional.empty()));

            compiledClassMember.value.ifPresent(outputValues::add);
            compiledClassMember.more.ifPresent(outputMore::add);
        }

        var value = String.join("", outputValues);
        var renderedClass = outputValues.isEmpty() ? Optional.<String>empty() : Optional.of(isPublic
                ? renderExportedMagmaClass(name, value)
                : renderMagmaClass(name, value));

        var renderedMore = outputMore.isEmpty()
                ? Optional.<String>empty()
                : Optional.of(renderObject(name, String.join("", outputMore)));

        return Optional.of(new State(renderedClass, renderedMore));
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

        String outputType;
        if (inputType.equals(LONG)) outputType = I64;
        else if (inputType.equals(INT)) outputType = I32;
        else outputType = inputType;

        var name = beforeSlice.substring(nameSeparator + 1);
        var value = inputContent.substring(valueSeparator + 1).strip();

        var mutabilityString = flags.contains(FINAL_KEYWORD) ? CONST_KEYWORD : LET_KEYWORD;
        var flagString = flags.contains("public") ? "pub " : "";
        var rendered = renderMagmaDefinition(flagString, mutabilityString, name, outputType, value);

        return Optional.of(flags.contains("static")
                ? new State(Optional.empty(), Optional.of(rendered))
                : new State(Optional.of(rendered), Optional.empty()));
    }

    static String renderObject(String name, String content) {
        return "object " + name + " {" + content + "\n}";
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
        return prefix + CLASS_KEYWORD + "def " + name + "() => {" + content + "\n}";
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

    record State(Optional<String> value, Optional<String> more) {
    }
}