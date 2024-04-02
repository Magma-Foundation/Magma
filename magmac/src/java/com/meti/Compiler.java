package com.meti;

import com.meti.java.ImportNode;
import com.meti.magma.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.meti.Lang.CLASS_KEYWORD_WITH_SPACE;

public class Compiler {
    static String compile(String input) {
        var args = split(input);

        var imports = new ArrayList<String>();
        var objects = new ArrayList<String>();
        var classes = new ArrayList<String>();

        for (String arg : args) {
            var state = compileRootStatement(arg.strip());
            state.importValue.ifPresent(imports::add);
            classes.addAll(state.instanceValues);

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
            if (c == '\'') {
                state.toggleSingleQuotes();
                state.append(c);
            } else if (!state.isInSingleQuotes) {
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
            } else {
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
                .orElse(new State(Optional.empty(),
                        Collections.emptyList(), Optional.empty(), Optional.empty()));
    }

    private static Optional<State> compileRecord(String input) {
        var index = input.indexOf(Lang.RECORD_KEYWORD);
        if (index == -1) return Optional.empty();

        var isPublic = input.startsWith(Lang.PUBLIC_KEYWORD);

        var nameStart = index + Lang.RECORD_KEYWORD.length();
        var nameEnd = input.indexOf('(');

        if (!(nameStart < nameEnd)) return Optional.empty();

        var name = input.substring(nameStart, nameEnd).strip();

        var bodyStart = input.indexOf('{');
        var bodyEnd = input.lastIndexOf('}');
        var membersString = compileMembers(input.substring(bodyStart + 1, bodyEnd), name);

        var rendered = new MagmaClassNodeBuilder()
                .withPrefix(isPublic ? Lang.EXPORT_KEYWORD_WITH_SPACE : "")
                .withName(name)
                .withContent(membersString.value)
                .build()
                .render();

        return Optional.of(new State(Optional.empty(), Optional.of(rendered).map(Collections::singletonList).orElse(Collections.emptyList()), Optional.empty(), Optional.empty()));
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
            var name = input.substring(index + Lang.INTERFACE_KEYWORD.length(), contentStart).strip();
            var membersResult = compileMembers(content.substring(1, content.length() - 1), name);

            var rendered = new MagmaTraitNode(isPublic ? Lang.EXPORT_KEYWORD_WITH_SPACE : "", name, "{" + membersResult.value + "\n}")
                    .render();

            return Optional.of(new State(Optional.of(rendered), Collections.emptyList(), Optional.empty(), Optional.empty()));
        }
        return Optional.empty();
    }

    private static Optional<State> compileClass(String input) {
        if (!input.contains(CLASS_KEYWORD_WITH_SPACE)) return Optional.empty();
        var isPublic = input.startsWith(Lang.PUBLIC_KEYWORD);
        var nameStart = input.indexOf(CLASS_KEYWORD_WITH_SPACE) + CLASS_KEYWORD_WITH_SPACE.length();

        var bodyStart = input.indexOf('{');
        var bodyEnd = input.lastIndexOf('}');
        if (bodyEnd == -1) return Optional.empty();

        var name = input.substring(nameStart, bodyStart).strip();
        var inputContent = input.substring(bodyStart + 1, bodyEnd);
        var membersResult = compileMembers(inputContent, name);

        var parameterString = membersResult.parameter.orElse("");

        var flags = new ArrayList<String>();
        if (isPublic) flags.add(Lang.EXPORT_KEYWORD);

        var flagString = flags.isEmpty() ? "" : String.join(" ", flags) + " ";
        var renderedClass = new MagmaMethodBuilder()
                .withPrefix(flagString + CLASS_KEYWORD_WITH_SPACE)
                .withName(name)
                .withContent("{" + membersResult.value() + "\n}")
                .withParameters(parameterString)
                .build()
                .render();

        Optional<String> renderedMore;
        if (membersResult.outputMore().isEmpty()) {
            renderedMore = Optional.empty();
        } else {
            String content = String.join("", membersResult.outputMore());
            renderedMore = Optional.of(new ObjectNode(flagString, name, content).render());
        }

        return Optional.of(new State(Optional.empty(), Optional.of(renderedClass).map(Collections::singletonList).orElse(Collections.emptyList()), renderedMore, Optional.empty()));
    }

    private static MembersResult compileMembers(String inputContent, String name) {
        var splitContent = split(inputContent);

        var outputValues = new ArrayList<String>();
        var outputMore = new ArrayList<String>();
        Optional<String> parameter = Optional.empty();

        for (var classMember : splitContent) {
            var compiledClassMember = compileMethod(classMember, name)
                    .or(() -> compileDefinition(classMember, false))
                    .orElse(new State(Optional.empty(),
                            Optional.of(classMember).map(Collections::singletonList).orElse(Collections.emptyList()), Optional.empty(), Optional.empty()));

            outputValues.addAll(compiledClassMember.instanceValues);
            compiledClassMember.staticValue.ifPresent(outputMore::add);
            parameter = compiledClassMember.parameter;
        }


        var value = outputValues
                .stream()
                .map(String::strip)
                .filter(line -> !line.isEmpty())
                .map(element -> "\n\t" + element)
                .collect(Collectors.joining());

        return new MembersResult(outputMore, value, parameter);
    }

    private static Optional<State> compileMethod(String input, String parentName) {
        var lines = Arrays.stream(input.split("\n")).toList();

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

        var paramEnd = methodString.indexOf(')');
        if (paramEnd == -1) return Optional.empty();

        var parameterString = methodString.substring(paramStart + 1, paramEnd).strip();
        String outputParamString;
        try {
            outputParamString = splitParameters(parameterString)
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .map((String input1) -> compileDefinition(input1, true))
                    .flatMap(Optional::stream)
                    .map(state -> state.instanceValues)
                    .flatMap(Collection::stream)
                    .collect(Collectors.joining(", "));
        } catch (Exception e) {
            throw new RuntimeException("Failed to compile parameter string: " + parameterString, e);
        }

        var before = methodString.substring(0, paramStart).strip();
        var separator = before.lastIndexOf(' ');

        String name;
        List<String> inputFlags;
        Optional<String> outputType;
        if (separator == -1) {
            if (!before.equals(parentName)) {
                return Optional.empty();
            } else {
                return Optional.of(new State(
                        Optional.empty(),
                        Optional.of("").map(Collections::singletonList).orElse(Collections.emptyList()), Optional.empty(),
                        Optional.of(outputParamString)));
            }
        } else {
            name = before.substring(separator + 1).strip();
            if (!isSymbol(name)) return Optional.empty();

            var flagsAndType = before.substring(0, separator).strip();
            var typeSeparator = flagsAndType.lastIndexOf(' ');

            if (typeSeparator == -1) {
                inputFlags = Collections.emptyList();
                outputType = Optional.ofNullable(compileType(flagsAndType));
            } else {
                inputFlags = Arrays.stream(flagsAndType.substring(0, typeSeparator).split(" ")).toList();

                try {
                    var typeString = flagsAndType.substring(typeSeparator + 1).strip();
                    outputType = Optional.ofNullable(compileType(typeString));
                } catch (Exception e) {
                    throw new RuntimeException("Failed to compile type string for input: " + input, e);
                }
            }
        }

        var instanceValues = new ArrayList<>(annotations.stream()
                .map(name1 -> new Annotation(name1, "").render())
                .toList());

        var contentStart = methodString.indexOf('{');

        var contentEnd = methodString.lastIndexOf('}');

        if (contentStart != -1 && contentEnd != -1) {
            if (!(paramEnd < contentStart)) return Optional.empty();
            var throwsString = methodString.substring(paramEnd + 1, contentStart).strip();

            var content = methodString.substring(contentStart, contentEnd + 1).strip();
            var magmaMethodBuilder = new MagmaMethodBuilder()
                    .withParameters(outputParamString)
                    .withName(name)
                    .withContent(content);

            var builder = outputType
                    .map(magmaMethodBuilder::withType)
                    .orElse(magmaMethodBuilder);

            MagmaMethodBuilder result;
            if (throwsString.isEmpty()) {
                result = builder.withExceptionString("");
            } else {
                var exceptionName = throwsString.substring("throws ".length()).strip();
                result = builder.withExceptionString(" ? " + exceptionName);
            }

            var rendered = result.build().render();
            if (inputFlags.contains("static"))
                return Optional.of(new State(Optional.empty(), Collections.emptyList(), Optional.of(rendered), Optional.empty()));

            instanceValues.add(rendered);
            return Optional.of(new State(Optional.empty(), instanceValues, Optional.empty(), Optional.empty()));
        } else if (contentStart == -1 && contentEnd == -1) {
            var rendered = new MagmaDeclaration("", "", name, "() => Void").render() + ";";
            return Optional.of(new State(Optional.empty(), Optional.of(rendered).map(Collections::singletonList).orElse(Collections.emptyList()), Optional.empty(), Optional.empty()));
        } else {
            return Optional.empty();
        }
    }

    private static Stream<String> splitParameters(String parameterString) {
        var list = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < parameterString.length(); i++) {
            var c = parameterString.charAt(i);
            if (c == ',' && depth == 0) {
                list.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '<') depth++;
                if (c == '>') depth--;
                builder.append(c);
            }
        }

        list.add(builder.toString());
        return list.stream()
                .map(String::strip)
                .filter(value -> !value.isEmpty());
    }

    private static boolean isSymbol(String name) {
        if (name.isEmpty()) return false;
        var first = name.charAt(0);
        var slice = name.substring(1);
        return Character.isLetter(first) &&
               IntStream.range(0, slice.length())
                       .mapToObj(slice::charAt)
                       .allMatch(Character::isLetterOrDigit);

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

    private static Optional<State> compileDefinition(String input, boolean isParameter) {
        var valueSeparator = input.indexOf('=');

        var beforeEnd = valueSeparator == -1 ? input.length() : valueSeparator;
        var beforeSlice = input.substring(0, beforeEnd).strip();
        var nameSeparator = beforeSlice.lastIndexOf(' ');
        if (nameSeparator == -1) return Optional.empty();

        var name = beforeSlice.substring(nameSeparator + 1);
        if (!isSymbol(name)) return Optional.empty();

        var flagsAndType = beforeSlice.substring(0, nameSeparator).strip();
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
        try {
            outputType = compileType(inputType);
        } catch (Exception e) {
            var format = "Failed to compile input type '%s' for input: %s";
            var message = format.formatted(inputType, input);
            throw new RuntimeException(message, e);
        }

        String mutabilityString;
        if (flags.contains(Lang.FINAL_KEYWORD)) mutabilityString = Lang.CONST_KEYWORD;
        else {
            if(isParameter) {
                mutabilityString = "";
            } else {
                mutabilityString = Lang.LET_KEYWORD;
            }
        }


        var flagString = flags.contains("public") ? "pub " : "";
        var withDeclaration = new MagmaDefinitionBuilder()
                .withFlags(flagString)
                .withMutability(mutabilityString)
                .withName(name)
                .withType(outputType);

        MagmaDefinitionBuilder withValue;
        if (valueSeparator == -1) {
            withValue = withDeclaration;
        } else {
            var value = input.substring(valueSeparator + 1).strip();
            withValue = withDeclaration.withValue(value);
        }

        var rendered = withValue.build().render();
        return Optional.of(flags.contains("static")
                ? new State(Optional.empty(), Collections.emptyList(), Optional.of(rendered), Optional.empty())
                : new State(Optional.empty(), Optional.of(rendered).map(Collections::singletonList).orElse(Collections.emptyList()), Optional.empty(), Optional.empty()));
    }

    private static String compileType(String inputType) {
        if (inputType.equals(Lang.LONG)) {
            return Lang.I64;
        } else if (inputType.equals(Lang.INT)) {
            return Lang.I32;
        } else if (inputType.equals(Lang.LOWER_VOID)) {
            return Lang.CAMEL_VOID;
        } else if (inputType.equals(Lang.BOOL)) {
            return Lang.CAMEL_BOOL;
        } else if (isSymbol(inputType)) return inputType;

        var genericStart = inputType.indexOf('<');
        var genericStop = inputType.lastIndexOf('>');
        if (genericStart != -1 && genericStop != -1) {
            var parent = inputType.substring(0, genericStart).strip();
            var genericString = inputType.substring(genericStart + 1, genericStop).strip();
            var child = compileType(genericString);
            return parent + "<" + child + ">";
        }

        var arrayStart = inputType.indexOf('[');
        var arrayEnd = inputType.lastIndexOf(']');

        if (arrayStart != -1 && arrayEnd != -1) {
            var parent = compileType(inputType.substring(0, arrayStart));
            return "Array<" + parent + ">";
        }

        throw new UnsupportedOperationException("Unknown type: " + inputType);
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
        return Optional.of(new State(Optional.of(child.equals("*") ? importNode.render() : new ImportNode(parent, "{ " + child + " } from ").render()), Collections.emptyList(), Optional.empty(), Optional.empty()));
    }

    private record MembersResult(ArrayList<String> outputMore, String value, Optional<String> parameter) {
    }

    static final class State {
        private final Optional<String> importValue;
        private final List<String> instanceValues;
        private final Optional<String> staticValue;
        private final Optional<String> parameter;

        State(Optional<String> importValue,
              List<String> instanceValues,
              Optional<String> staticValue,
              Optional<String> parameter) {
            this.importValue = importValue;
            this.instanceValues = instanceValues;
            this.staticValue = staticValue;
            this.parameter = parameter;
        }

        public Optional<String> importValue() {
            return importValue;
        }

        public Optional<String> staticValue() {
            return staticValue;
        }

        public Optional<String> parameter() {
            return parameter;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (State) obj;
            return Objects.equals(this.importValue, that.importValue) &&
                   Objects.equals(this.instanceValues, that.instanceValues) &&
                   Objects.equals(this.staticValue, that.staticValue) &&
                   Objects.equals(this.parameter, that.parameter);
        }

        @Override
        public int hashCode() {
            return Objects.hash(importValue, instanceValues, staticValue, parameter);
        }

        @Override
        public String toString() {
            return "State[" +
                   "importValue=" + importValue + ", " +
                   "instanceValue=" + instanceValues + ", " +
                   "staticValue=" + staticValue + ", " +
                   "parameter=" + parameter + ']';
        }

    }
}