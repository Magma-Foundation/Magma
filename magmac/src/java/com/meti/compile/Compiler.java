package com.meti.compile;

import com.meti.collect.JavaString;
import com.meti.collect.Range;
import com.meti.node.*;
import com.meti.option.Option;
import com.meti.option.ThrowableOption;
import com.meti.result.Result;
import com.meti.rule.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class Compiler {
    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String EXPORT_KEYWORD = "export";
    public static final String EXPORT_KEYWORD_WITH_SPACE = EXPORT_KEYWORD + " ";
    public static final String PUBLIC_KEYWORD = "public";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = PUBLIC_KEYWORD + " ";
    public static final String IMPORT_KEYWORD_WITH_SPACE = "import ";
    public static final char STATEMENT_END = ';';
    public static final String STATIC_KEYWORD = "static";
    public static final JavaString STATIC_STRING = new JavaString(STATIC_KEYWORD);
    public static final String STATIC_KEYWORD_WITH_SPACE = STATIC_KEYWORD + " ";
    public static final char BLOCK_START = '{';
    public static final char BLOCK_END = '}';
    public static final RequireBoth BLOCK = new RequireBoth(BLOCK_START, new StringRule("content"), BLOCK_END);
    public static final FirstSliceRule MAGMA_IMPORT = new FirstSliceRule("from", new RequireBoth(IMPORT_KEYWORD_WITH_SPACE + BLOCK_START + " ", new StringRule("child"), " " + BLOCK_END + " "), new RequireRightChar(new StringRule("parent"), STATEMENT_END));
    public static final String INT_KEYWORD = "int";
    public static final String I32_KEYWORD = "I32";
    public static final String LONG_KEYWORD = "long";
    public static final String I64_KEYWORD = "I64";
    public static final char VALUE_SEPARATOR = '=';
    public static final FirstCharRule JAVA_DEFINITION = new FirstCharRule(VALUE_SEPARATOR, new StripRule(new LastCharSeparatorRule(new OrRule(new FirstCharRule(
            ' ', new StringListRule("modifiers", " "),
            new StringRule("type")),
            new StringRule("type")), ' ', new StringRule("name"))),
            new RequireRightChar(new StripRule(new StringRule("value")), STATEMENT_END));
    public static final String FINAL_KEYWORD = "final";
    public static final String LET_KEYWORD_WITH_SPACE = "let ";
    public static final String CONST_KEYWORD_WITH_SPACE = "const ";
    public static final String TEMP_SEPARATOR = "() =>";
    public static final String DEF_KEYWORD = "def";
    public static final String DEF_KEYWORD_WITH_SPACE = "def ";
    public static final FirstSliceRule MAGMA_FUNCTION = new FirstSliceRule(TEMP_SEPARATOR, new FirstSliceRule(CLASS_KEYWORD_WITH_SPACE + DEF_KEYWORD_WITH_SPACE, new OrRule(new RequireRightSlice(" ", new StringListRule("modifiers", " ")), new EmptyRule()), new StringRule("name")), new RequireLeft(' ', BLOCK));

    public static String renderJavaDefinition(String type, String name, String value) {
        return renderJavaDefinition(new MapNodePrototype()
                .withListOfStrings("modifiers", emptyList())
                .withString("type", new JavaString(type))
                .withString("name", new JavaString(name))
                .withString("value", new JavaString(value))
                .complete(new JavaString("definition")));
    }

    public static String renderJavaDefinition(Node node) {
        return JAVA_DEFINITION.fromNode(node)
                .orElse(JavaString.EMPTY)
                .value();
    }

    public static String renderMagmaDefinitionUnsafe(String name, String type, String value) {
        return renderMagmaDefinitionUnsafe(getJavaStrings(emptyList(), LET_KEYWORD_WITH_SPACE, name), type, value);
    }

    public static String renderMagmaDefinitionUnsafe(JavaString modifierString, String name, String type, String value) {
        return renderMagmaDefinitionUnsafe(getJavaStrings(singletonList(modifierString), LET_KEYWORD_WITH_SPACE, name), type, value);
    }

    public static String renderMagmaDefinitionUnsafe(List<JavaString> segments, String type, String value) {
        var s2 = new StringListRule("segments", " ")
                .fromNode(new MapNodePrototype()
                        .withListOfStrings("segments", segments)
                        .complete(new JavaString("definition")))
                .orElse(JavaString.EMPTY)
                .value();

        var s3 = type + " ";
        var s = s2 + " : " + s3;

        var s1 = " " + value + ";";

        return s + VALUE_SEPARATOR + s1;
    }

    private static List<JavaString> getJavaStrings(List<JavaString> modifiersString, String mutabilityString, String name) {
        List<JavaString> segments = new ArrayList<>(modifiersString);
        segments.add(new JavaString(mutabilityString));
        segments.add(new JavaString(name));
        return segments;
    }

    public static String renderMagmaImportUnsafe(Node node) {
        return MAGMA_IMPORT.fromNode(node)
                .orElse(JavaString.EMPTY)
                .value();
    }

    public static String renderMagmaFunctionUnsafe(String name) {
        return renderMagmaFunctionUnsafe(createFunctionNode(emptyList(), name, ""));
    }

    public static String renderMagmaFunctionUnsafe(Node node) {
        return MAGMA_FUNCTION.fromNode(node).orElse(JavaString.EMPTY).value();
    }

    public static Node createFunctionNode(List<JavaString> modifiers, String name, String content) {
        var nodePrototype = new MapNodePrototype()
                .withString("name", new JavaString(name))
                .withString("content", new JavaString(content));

        var nodePrototype1 = modifiers.isEmpty() ? nodePrototype : nodePrototype.withListOfStrings("modifiers", modifiers);
        return nodePrototype1.complete(new JavaString("function"));
    }

    public static JavaString run(JavaString input) {
        var lines = split(input);

        var imports = lines.subList(0, lines.size() - 1);
        var classString = lines.get(lines.size() - 1);

        var beforeString = imports.stream()
                .map(Compiler::compileImport)
                .map(option -> option.orElse(JavaString.EMPTY))
                .reduce(JavaString::concatOwned)
                .orElse(JavaString.EMPTY);

        var compiledClass = compileClass(classString).$();
        return beforeString.concatOwned(compiledClass);
    }

    private static Result<JavaString, UnsupportedOperationException> compileClass(JavaString classString) {
        return classString.firstRangeOfSlice(CLASS_KEYWORD_WITH_SPACE).flatMap(classIndex -> {
            return classString.firstIndexOfChar(BLOCK_START).flatMap(contentStart -> {
                return classString.lastIndexOfChar(BLOCK_END).flatMap(contentEnd -> {
                    var nameStart = classIndex.endIndex();

                    var className = classString.sliceBetween(nameStart, contentStart).strip();

                    return contentStart.next().map(afterContentStart -> {
                        var inputContent = classString.sliceBetween(afterContentStart, contentEnd);

                        var modifiers1 = classString.startsWithSlice(PUBLIC_KEYWORD_WITH_SPACE)
                                ? Collections.singletonList(new JavaString(EXPORT_KEYWORD))
                                : Collections.<JavaString>emptyList();

                        var stateResultOption = lexDefinition(inputContent)
                                .map(built -> {
                                    var inputType = built.apply("type")
                                            .flatMap(Attribute::asString)
                                            .orElse(JavaString.EMPTY);

                                    var outputType = compileType(inputType);
                                    return built.with("type", new StringAttribute(outputType)).orElse(built);
                                })
                                .map(Compiler::parseDefinition).<StateResult>map(parsed -> {
                                    var modifiers = new ArrayList<>(parsed.apply("modifiers")
                                            .flatMap(Attribute::asListOfStrings)
                                            .orElse(emptyList()));

                                    if (modifiers.contains(STATIC_STRING)) {
                                        modifiers.remove(STATIC_STRING);

                                        return new StaticResult(parsed.with("modifiers", new StringListAttribute(modifiers)).orElse(parsed));
                                    }
                                    return new InstanceResult(parsed);
                                });

                        var instanceValue = stateResultOption
                                .flatMap(StateResult::findInstanceValue)
                                .map(Compiler::renderMagmaDefinition);

                        var nodePrototype = new MapNodePrototype()
                                .withString("name", className)
                                .withString("content", instanceValue.orElse(JavaString.EMPTY));

                        var nodePrototype1 = modifiers1.isEmpty() ? nodePrototype : nodePrototype.withListOfStrings("modifiers", modifiers1);

                        var instanceFunction = new JavaString(renderMagmaFunctionUnsafe(nodePrototype1.complete(new JavaString("function"))));

                        var objectString = stateResultOption
                                .flatMap(StateResult::findStaticValue)
                                .map(Compiler::renderMagmaDefinition)
                                .map(staticValue -> renderObject(className, staticValue))
                                .orElse(JavaString.EMPTY);

                        return instanceFunction.concatOwned(objectString);
                    });
                });
            });
        }).into(ThrowableOption::new).orElseThrow(() -> new UnsupportedOperationException("No class present."));
    }

    private static JavaString renderObject(JavaString className, JavaString staticValue) {
        return new JavaString(renderObjectUnsafe(className.value(), staticValue.value()));
    }


    private static Option<Node> lexDefinition(JavaString inputContent) {
        return JAVA_DEFINITION
                .fromString(inputContent)
                .map(prototype -> prototype.complete(new JavaString("definition")));
    }

    private static Node parseDefinition(Node node) {
        var modifiers = node.apply("modifiers").flatMap(Attribute::asListOfStrings).orElse(emptyList());
        var name = node.apply("name").flatMap(Attribute::asString).orElse(JavaString.EMPTY);
        var type = node.apply("type").flatMap(Attribute::asString).orElse(JavaString.EMPTY);
        var value = node.apply("value").flatMap(Attribute::asString).orElse(JavaString.EMPTY);

        List<JavaString> newModifiers;
        if (modifiers.isEmpty()) newModifiers = emptyList();
        else newModifiers = modifiers.stream()
                .filter(modifier -> modifier.equalsToSlice(PUBLIC_KEYWORD) || modifier.equalsToSlice(STATIC_KEYWORD))
                .collect(Collectors.toList());

        var mutabilityString = new JavaString(modifiers.contains(new JavaString(FINAL_KEYWORD))
                ? CONST_KEYWORD_WITH_SPACE
                : LET_KEYWORD_WITH_SPACE);

        return new MapNodePrototype()
                .withListOfStrings("modifiers", newModifiers)
                .withString("mutabilityString", mutabilityString)
                .withString("name", name)
                .withString("outputType", type)
                .withString("after", value)
                .complete(new JavaString("definition"));
    }

    private static JavaString compileType(JavaString inputType) {
        return new JavaString(compileTypeUnsafe(inputType.value()));
    }

    private static List<JavaString> split(JavaString input) {
        return split(input.value()).stream().map(JavaString::new).collect(Collectors.toList());
    }

    private static ArrayList<String> split(String input) {
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
        return lines;
    }

    private static Option<JavaString> compileImport(JavaString beforeString) {
        return beforeString.firstRangeOfSlice(IMPORT_KEYWORD_WITH_SPACE).flatMap(importString -> {
            var segmentStart = beforeString.firstRangeOfSlice(IMPORT_KEYWORD_WITH_SPACE + STATIC_KEYWORD_WITH_SPACE)
                    .map(Range::endIndex)
                    .orElse(importString.endIndex());

            var set = beforeString.sliceFrom(segmentStart);
            return set.lastIndexOfChar('.').flatMap(last -> {
                var parent = set.sliceTo(last);

                return last.next().map(nextLast -> {
                    var child = set.sliceFrom(nextLast);

                    return renderMagmaImport(parent, child);
                });
            });
        });
    }

    private static JavaString renderMagmaImport(JavaString parent, JavaString child) {
        return new JavaString(renderMagmaImportUnsafe(new MapNodePrototype()
                .withString("parent", new JavaString(parent.value()))
                .withString("child", new JavaString(child.value()))
                .complete(new JavaString("import"))));
    }

    private static JavaString renderMagmaDefinition(Node node) {
        return new JavaString(renderMagmaDefinitionUnsafe(
                getJavaStrings(node.apply("modifiers").flatMap(Attribute::asListOfStrings).orElse(emptyList()), node.apply("mutabilityString").flatMap(Attribute::asString).orElse(JavaString.EMPTY).value(), node.apply("name").flatMap(Attribute::asString).orElse(JavaString.EMPTY).value()), node.apply("outputType").flatMap(Attribute::asString).orElse(JavaString.EMPTY).value(),
                node.apply("after").flatMap(Attribute::asString).orElse(JavaString.EMPTY).value()));
    }

    private static String compileTypeUnsafe(String inputType) {
        return switch (inputType) {
            case INT_KEYWORD -> I32_KEYWORD;
            case LONG_KEYWORD -> I64_KEYWORD;
            default -> inputType;
        };
    }

    public static String renderJavaClass(String name) {
        return renderJavaClass("", name);
    }

    public static String renderJavaClass(String modifiersString, String name) {
        return renderJavaClass(modifiersString, name, "");
    }

    public static String renderJavaClass(String modifiersString, String name, String content) {
        return modifiersString + CLASS_KEYWORD_WITH_SPACE + name + " " + BLOCK.fromNode(new MapNodePrototype()
                        .withString("content", new JavaString(content))
                        .complete(new JavaString("block")))
                .orElse(JavaString.EMPTY)
                .value();
    }

    public static String renderJavaImport(String parent, String child) {
        return renderJavaImport(parent, child, "");
    }

    public static String renderJavaImport(String parent, String child, String modifierString) {
        return IMPORT_KEYWORD_WITH_SPACE + modifierString + parent + "." + child + STATEMENT_END;
    }

    public static String renderObjectUnsafe(String name, String content) {
        return "object " + name + " " + BLOCK_START + content + BLOCK_END;
    }
}
