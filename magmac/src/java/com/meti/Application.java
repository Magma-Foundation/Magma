package com.meti;

import java.util.*;
import java.util.stream.Collectors;

import static com.meti.Lang.*;

public class Application {
    static String compileJavaToMagma(String input) throws CompileException {
        var classStart = input.indexOf(CLASS_KEYWORD);
        if(classStart == -1) throw createUnknownInputError(input);

        var separator = input.substring(0, classStart).indexOf(';');
        if (separator == -1) {
            return compileClass(input);
        } else {
            return compileClass(input.substring(separator + 1));
        }
    }

    private static String compileClass(String input) throws CompileException {
        var classIndex = input.indexOf(CLASS_KEYWORD);
        if (classIndex == -1) throw createUnknownInputError(input);

        var blockStart = input.indexOf(BLOCK_START);
        var blockEnd = input.lastIndexOf(BLOCK_END);

        var className = input.substring(classIndex + CLASS_KEYWORD.length(), blockStart);
        var inputContent = input.substring(blockStart + 1, blockEnd);
        var members = Arrays.stream(inputContent.split(";"))
                .map(String::strip).filter(value -> !value.isEmpty())
                .map(Application::compileJavaDefinition)
                .flatMap(Optional::stream)
                .toList();

        var staticContent = members.stream()
                .map(Member::staticValue)
                .flatMap(Optional::stream)
                .collect(Collectors.joining());

        var renderedStatic = staticContent.isEmpty() ? "" : renderObject(className, staticContent);
        var instanceContent = members.stream()
                .map(Member::instanceValue)
                .flatMap(Optional::stream)
                .collect(Collectors.joining());

        var body = renderBlock(instanceContent);
        var rendered = renderMagmaFunction(className, body);
        var renderedInstance = input.startsWith(PUBLIC_KEYWORD_WITH_SPACE) ? EXPORT_KEYWORD + rendered : rendered;

        return renderedStatic + renderedInstance;
    }

    private static Optional<Member> compileJavaDefinition(String inputContent) {
        var beforeEnd = inputContent.indexOf(VALUE_SEPARATOR);
        if (beforeEnd == -1) return Optional.empty();

        var before = inputContent.substring(0, beforeEnd);
        var nameSeparator = before.lastIndexOf(' ');
        if (nameSeparator == -1) return Optional.empty();

        var beforeName = before.substring(0, nameSeparator);
        String inputType;
        Set<String> flags;

        var typeSeparator = beforeName.lastIndexOf(' ');
        if (typeSeparator == -1) {
            inputType = beforeName;
            flags = Collections.emptySet();
        } else {
            inputType = beforeName.substring(typeSeparator + 1);
            flags = Arrays.stream(beforeName.substring(0, typeSeparator).split(" ")).map(String::strip).filter(value -> !value.isEmpty()).collect(Collectors.toSet());
        }

        var outputType = inputType.equals(INT_TYPE) ? I16_TYPE : inputType;

        var name = before.substring(nameSeparator + 1);
        var value = inputContent.substring(beforeEnd + VALUE_SEPARATOR.length());
        var mutabilityModifier = flags.contains(FINAL_KEYWORD) ? CONST_KEYWORD_WITH_SPACE : LET_KEYWORD;
        var flagString = flags.contains(PUBLIC_KEYWORD) ? PUB_KEYWORD_WITH_SPACE : "";
        var rendered = renderMagmaDefinition(flagString, mutabilityModifier, name, outputType, value);

        var value1 = flags.contains(STATIC_KEYWORD) ? new StaticMember(rendered) : new InstanceMember(rendered);
        return Optional.of(value1);
    }

    static CompileException createUnknownInputError(String input) {
        return new CompileException("Unknown input: " + input);
    }

    static String compileMagmaToJava(String input) throws CompileException {
        return compileMagmaToJava(input, Collections.emptyList());
    }

    static String compileMagmaToJava(String input, List<String> namespace) throws CompileException {
        var prefixIndex = input.indexOf(CLASS_KEYWORD + DEF_KEYWORD);
        if (prefixIndex == -1) throw createUnknownInputError(input);
        List<JavaDefinition> staticMembers;
        if (input.startsWith(OBJECT_KEYWORD)) {
            var staticContent = input.substring(input.indexOf('{') + 1, input.indexOf('}'));

            staticMembers = Arrays.stream(staticContent.split(";"))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .map(Application::compileMagmaDefinition)
                    .flatMap(Optional::stream)
                    .map(definition -> definition.withFlag(STATIC_KEYWORD))
                    .toList();
        } else {
            staticMembers = Collections.emptyList();
        }

        var nameStart = prefixIndex + (CLASS_KEYWORD + DEF_KEYWORD).length();
        var className = input.substring(nameStart, input.indexOf("(", nameStart));

        var instanceContentStart = input.indexOf('{', nameStart);
        var instanceContentEnd = input.lastIndexOf('}');
        var instanceContent = input.substring(instanceContentStart + 1, instanceContentEnd);
        var instanceMembers = Arrays.stream(instanceContent.split(";"))
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .map(Application::compileMagmaDefinition)
                .flatMap(Optional::stream)
                .toList();

        var copy = new ArrayList<>(staticMembers);
        copy.addAll(instanceMembers);

        var renderedMembers = copy.stream().map(JavaDefinition::render).collect(Collectors.joining());
        var rendered = renderJavaClass(className, renderBlock(renderedMembers));

        var renderedClass = input.startsWith(EXPORT_KEYWORD) ? PUBLIC_KEYWORD_WITH_SPACE + rendered : rendered;
        var packageString = namespace.isEmpty() ? "" : renderPackage(namespace.get(0));
        return packageString + renderedClass;
    }

    private static Optional<JavaDefinition> compileMagmaDefinition(String content) {
        var letIndex = content.indexOf(LET_KEYWORD);
        var constIndex = content.indexOf(CONST_KEYWORD_WITH_SPACE);
        if (letIndex == -1 && constIndex == -1) return Optional.empty();

        var isMutable = letIndex != -1;

        var typeSeparator = content.indexOf(MAGMA_TYPE_SEPARATOR);
        var nameStart = isMutable ? (LET_KEYWORD.length() + letIndex) : (CONST_KEYWORD_WITH_SPACE.length() + constIndex);
        var name = content.substring(nameStart, typeSeparator);

        var valueSeparator = content.indexOf(VALUE_SEPARATOR);
        var inputType = content.substring(typeSeparator + MAGMA_TYPE_SEPARATOR.length(), valueSeparator);
        var outputType = inputType.equals(I16_TYPE) ? INT_TYPE : inputType;

        var value = content.substring(valueSeparator + VALUE_SEPARATOR.length());
        var flags = Arrays.stream(content.substring(0, nameStart).strip().split(" "))
                .map(String::strip)
                .filter(arg -> !arg.isEmpty())
                .collect(Collectors.toSet());

        var outputFlags = new ArrayList<String>();
        if (flags.contains(PUB_KEYWORD)) outputFlags.add(PUBLIC_KEYWORD);
        if (flags.contains(CONST_KEYWORD)) outputFlags.add(FINAL_KEYWORD);

        return Optional.of(new JavaDefinition(outputFlags, name, outputType, value));
    }
}