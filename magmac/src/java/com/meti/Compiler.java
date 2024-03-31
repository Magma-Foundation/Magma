package com.meti;

import com.meti.java.ImportNode;
import com.meti.magma.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Compiler {
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
                .or(() -> compileRecord(input))
                .or(() -> compileInterface(input))
                .orElse(new State(Optional.empty(), Optional.empty(), Optional.empty()));
    }

    private static Optional<State> compileRecord(String input) {
        var index = input.indexOf(Lang.RECORD_KEYWORD);
        if (index == -1) return Optional.empty();

        var isPublic = input.startsWith(Lang.PUBLIC_KEYWORD);

        var name = input.substring(index + Lang.RECORD_KEYWORD.length(), input.indexOf('(')).strip();

        var bodyStart = input.indexOf('{');
        var bodyEnd = input.lastIndexOf('}');
        var membersString = compileMembers(input.substring(bodyStart + 1, bodyEnd));

        var rendered = new MagmaClassNodeBuilder()
                .withPrefix(isPublic ? Lang.EXPORT_KEYWORD : "")
                .withName(name)
                .withContent(membersString.value)
                .build()
                .render();

        return Optional.of(new State(Optional.empty(), Optional.of(rendered), Optional.empty()));
    }

    private static Optional<State> compileInterface(String input) {
        var index = input.indexOf(Lang.INTERFACE_KEYWORD);
        if (index != -1) {
            var isPublic = input.startsWith(Lang.PUBLIC_KEYWORD);

            var contentStart = input.indexOf('{');
            if (contentStart == -1) return Optional.empty();

            var contentEnd = input.lastIndexOf('}');
            if (contentEnd == -1) return Optional.empty();

            var content = input.substring(contentStart, contentEnd + 1);
            var membersResult = compileMembers(content.substring(1, content.length() - 1));

            var name = input.substring(index + Lang.INTERFACE_KEYWORD.length(), contentStart).strip();
            var rendered = new MagmaTraitNode(isPublic ? Lang.EXPORT_KEYWORD : "", name, "{" + membersResult.value + "\n}")
                    .render();

            return Optional.of(new State(Optional.of(rendered), Optional.empty(), Optional.empty()));
        }
        return Optional.empty();
    }

    private static Optional<State> compileClass(String input) {
        if (!input.contains(Lang.CLASS_KEYWORD)) return Optional.empty();
        var isPublic = input.startsWith(Lang.PUBLIC_KEYWORD);
        var nameStart = input.indexOf(Lang.CLASS_KEYWORD) + Lang.CLASS_KEYWORD.length();

        var bodyStart = input.indexOf('{');
        var bodyEnd = input.lastIndexOf('}');
        if (bodyEnd == -1) return Optional.empty();

        var name = input.substring(nameStart, bodyStart).strip();
        var inputContent = input.substring(bodyStart + 1, bodyEnd);
        var membersResult = compileMembers(inputContent);
        var flagString = isPublic ? Lang.EXPORT_KEYWORD : "";

        var renderedClass = new MagmaClassNodeBuilder().withPrefix(flagString).withName(name).withContent(membersResult.value()).build().render();

        Optional<String> renderedMore;
        if (membersResult.outputMore().isEmpty()) {
            renderedMore = Optional.empty();
        } else {
            String content = String.join("", membersResult.outputMore());
            renderedMore = Optional.of(new ObjectNode(flagString, name, content).render());
        }

        return Optional.of(new State(Optional.empty(), Optional.of(renderedClass), renderedMore));
    }

    private static MembersResult compileMembers(String inputContent) {
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
        return new MembersResult(outputMore, value);
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
                .map(name1 -> new Annotation(name1, "").renderAnnotation())
                .collect(Collectors.joining());

        var paramEnd = methodString.indexOf(')');
        if (paramEnd == -1) return Optional.empty();

        var contentStart = methodString.indexOf('{');

        var contentEnd = methodString.lastIndexOf('}');

        if (contentStart != -1 && contentEnd != -1) {
            if (!(paramEnd < contentStart)) return Optional.empty();
            var throwsString = methodString.substring(paramEnd + 1, contentStart).strip();

            var content = methodString.substring(contentStart, contentEnd + 1).strip();
            var builder = new MagmaMethodBuilder()
                    .withPrefix(annotationsString)
                    .withName(name)
                    .withType(outputType)
                    .withContent(content);

            MagmaMethodBuilder result;
            if (throwsString.isEmpty()) {
                result = builder.withExceptionString("");
            } else {
                var exceptionName = throwsString.substring("throws ".length()).strip();
                result = builder.withExceptionString(" ? " + exceptionName);
            }

            var rendered = result.build().render();
            return inputFlags.contains("static")
                    ? Optional.of(new State(Optional.empty(), Optional.empty(), Optional.of(rendered)))
                    : Optional.of(new State(Optional.empty(), Optional.of(rendered), Optional.empty()));
        } else if (contentStart == -1 && contentEnd == -1) {
            var rendered = new MagmaDefinitionHeader("", "", name, "() => Void").render() + ";";
            return Optional.of(new State(Optional.empty(), Optional.of(rendered), Optional.empty()));
        } else {
            return Optional.empty();
        }
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

        var mutabilityString = flags.contains(Lang.FINAL_KEYWORD) ? Lang.CONST_KEYWORD : Lang.LET_KEYWORD;
        var flagString = flags.contains("public") ? "pub " : "";
        var rendered = new MagmaDefinitionBuilder()
                .withFlags(flagString)
                .withMutability(mutabilityString)
                .withName(name)
                .withType(outputType)
                .withValue(value)
                .build()
                .render();

        return Optional.of(flags.contains("static")
                ? new State(Optional.empty(), Optional.empty(), Optional.of(rendered))
                : new State(Optional.empty(), Optional.of(rendered), Optional.empty()));
    }

    private static String compileType(String inputType) {
        return switch (inputType) {
            case Lang.LONG -> Lang.I64;
            case Lang.INT -> Lang.I32;
            case Lang.LOWER_VOID -> Lang.CAMEL_VOID;
            default -> inputType;
        };
    }

    private static Optional<State> compileImport(String input) {
        if (!input.startsWith(Lang.IMPORT_KEYWORD)) return Optional.empty();
        var isStatic = input.startsWith(Lang.IMPORT_STATIC);
        var importKeyword = isStatic ? Lang.IMPORT_STATIC : Lang.IMPORT_KEYWORD;
        var segmentsString = input.substring(importKeyword.length());

        var separator = segmentsString.lastIndexOf('.');
        var parent = segmentsString.substring(0, separator);
        var child = segmentsString.substring(separator + 1);

        ImportNode importNode = new ImportNode(parent, "");
        return Optional.of(new State(Optional.of(child.equals("*") ? importNode.render() : new ImportNode(parent, "{ " + child + " } from ").render()), Optional.empty(), Optional.empty()));
    }

    private record MembersResult(ArrayList<String> outputMore, String value) {
    }

    record State(Optional<String> importValue, Optional<String> instanceValue, Optional<String> staticValue) {
    }
}