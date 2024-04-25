package com.meti.compile;

import com.meti.collect.JavaString;
import com.meti.collect.Range;
import com.meti.collect.Tuple;
import com.meti.node.Attribute;
import com.meti.node.MapNode;
import com.meti.node.Node;
import com.meti.node.StringAttribute;
import com.meti.option.Option;
import com.meti.option.ThrowableOption;
import com.meti.result.Result;

import java.util.*;
import java.util.stream.Collectors;

public class Compiler {
    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String EXPORT_KEYWORD_WITH_SPACE = "export ";
    public static final String PUBLIC_KEYWORD = "public";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = PUBLIC_KEYWORD + " ";
    public static final String IMPORT_KEYWORD_WITH_SPACE = "import ";
    public static final char STATEMENT_END = ';';
    public static final String STATIC_KEYWORD = "static";
    public static final String STATIC_KEYWORD_WITH_SPACE = STATIC_KEYWORD + " ";
    public static final char BLOCK_START = '{';
    public static final char BLOCK_END = '}';
    public static final String INT_KEYWORD = "int";
    public static final String I32_KEYWORD = "I32";
    public static final String LONG_KEYWORD = "long";
    public static final String I64_KEYWORD = "I64";
    public static final char VALUE_SEPARATOR = '=';
    public static final String FINAL_KEYWORD = "final";
    public static final String FINAL_KEYWORD_WITH_SPACE = FINAL_KEYWORD + " ";
    public static final String LET_KEYWORD_WITH_SPACE = "let ";
    public static final String CONST_KEYWORD_WITH_SPACE = "const ";

    public static String renderJavaDefinition(String type, String name, String value) {
        return renderJavaDefinition("", type, name, value);
    }

    public static String renderJavaDefinition(String modifiersString, String type, String name, String value) {
        return modifiersString + type + " " + name + " " + VALUE_SEPARATOR + " " + value + ";";
    }

    public static String renderMagmaDefinitionUnsafe(String name, String type, String value) {
        return renderMagmaDefinitionUnsafe("", name, type, value);
    }

    public static String renderMagmaDefinitionUnsafe(String modifierString, String name, String type, String value) {
        return renderMagmaDefinitionUnsafe(modifierString, LET_KEYWORD_WITH_SPACE, name, type, value);
    }

    public static String renderMagmaDefinitionUnsafe(String modifierString, String mutabilityString, String name, String type, String value) {
        return modifierString + mutabilityString + name + " : " + type + " " + VALUE_SEPARATOR + " " + value + ";";
    }

    private static String renderBlock(String content) {
        return BLOCK_START + content + BLOCK_END;
    }

    public static String renderMagmaImportUnsafe(String parent, String child) {
        return IMPORT_KEYWORD_WITH_SPACE + "{ " + child + " } from " + parent + STATEMENT_END;
    }

    public static String renderMagmaFunctionUnsafe(String name) {
        return renderMagmaFunctionUnsafe("", name);
    }

    public static String renderMagmaFunctionUnsafe(String modifiersString, String name) {
        return renderMagmaFunctionUnsafe(modifiersString, name, "");
    }

    public static String renderMagmaFunctionUnsafe(String modifiersString, String name, String content) {
        return modifiersString + CLASS_KEYWORD_WITH_SPACE + "def " + name + "() =>" + " " + renderBlock(content);
    }

    public static JavaString run(JavaString input) {
        var lines = split(input);

        var imports = lines.subList(0, lines.size() - 1);
        var classString = lines.get(lines.size() - 1);

        var beforeString = imports.stream()
                .map(Compiler::compileImport)
                .map(option -> option.orElse(JavaString.EMPTY))
                .reduce(JavaString::concat)
                .orElse(JavaString.EMPTY);

        var compiledClass = compileClass(classString).$();
        return beforeString.concat(compiledClass);
    }

    private static Result<JavaString, UnsupportedOperationException> compileClass(JavaString classString) {
        return classString.firstRangeOfSlice(CLASS_KEYWORD_WITH_SPACE).flatMap(classIndex -> {
            return classString.firstIndexOfChar(BLOCK_START).flatMap(contentStart -> {
                return classString.lastIndexOfChar(BLOCK_END).flatMap(contentEnd -> {
                    var nameStart = classIndex.endIndex();

                    var className = classString.sliceBetween(nameStart, contentStart).strip();
                    var modifierString = classString.value().startsWith(PUBLIC_KEYWORD_WITH_SPACE)
                            ? new JavaString(EXPORT_KEYWORD_WITH_SPACE)
                            : JavaString.EMPTY;

                    return contentStart.next().map(afterContentStart -> {
                        var inputContent = classString.sliceBetween(afterContentStart, contentEnd);

                        return compileDefinition(inputContent).map(content -> {
                            var instanceValue = content.findInstanceValue().orElse(JavaString.EMPTY);
                            var instanceFunction = renderMagmaFunction(modifierString, className, instanceValue);
                            var staticValueOptional = content.findStaticValue();

                            var objectString = staticValueOptional
                                    .map(staticValue -> renderObject(className, staticValue))
                                    .orElse(JavaString.EMPTY);

                            return instanceFunction.concat(objectString);
                        }).orElse(renderMagmaFunction(modifierString, className, JavaString.EMPTY));
                    });
                });
            });
        }).into(ThrowableOption::new).orElseThrow(() -> new UnsupportedOperationException("No class present."));
    }

    private static JavaString renderObject(JavaString className, JavaString staticValue) {
        return new JavaString(renderObjectUnsafe(className.value(), staticValue.value()));
    }

    private static JavaString renderMagmaFunction(JavaString modifierString, JavaString name, JavaString content) {
        return new JavaString(renderMagmaFunctionUnsafe(modifierString, name, content));
    }

    private static String renderMagmaFunctionUnsafe(JavaString modifierString, JavaString className, JavaString content) {
        return renderMagmaFunctionUnsafe(modifierString.value(), className.value(), content.value());
    }


    private static Option<StateResult> compileDefinition(JavaString inputContent) {
        return inputContent.splitAtFirstIndexOfCharExclusive(VALUE_SEPARATOR).flatMap(valueSlices -> {
            var before = valueSlices.a().strip();
            return before.splitAtLastIndexOfCharExclusive(' ').map(separator -> {
                var modifiersAndType = separator.a();
                var name = separator.b();
                var withName = MapNode.Builder(new JavaString("definition"))
                        .withString("name", name);

                var tuple = modifiersAndType.splitAtFirstIndexOfCharExclusive(' ').map(lastIndex -> {
                    var modifiersString = lastIndex.a().strip();
                    var typeString = lastIndex.b().strip();

                    var modifiers = new HashSet<>(modifiersString.splitBySlice(" "));

                    return withName.withListOfStrings("modifiers", new ArrayList<>(modifiers)).withString("type", typeString);
                }).orElseGet(() -> {
                    return withName.withListOfStrings("modifiers", new ArrayList<>(Collections.emptyList())).withString("type", modifiersAndType);
                });

                var b = valueSlices.b().strip();
                var value = b.sliceTo(b.firstIndexOfChar(STATEMENT_END).orElse(b.end())).strip();
                var built = tuple.withString("value", value).complete();

                var modifiers = built.apply("modifiers").flatMap(Attribute::asListOfStrings).orElse(Collections.emptyList());
                var outputType = compileType(built.apply("type").flatMap(Attribute::asString).orElse(JavaString.EMPTY));
                var withNewType = built.with("type", new StringAttribute(outputType)).orElse(built);

                var parsed = parseDefinition(withNewType);

                var rendered = renderMagmaDefinition(parsed);
                return modifiers.contains(new JavaString(STATIC_KEYWORD)) ? new StaticResult(rendered) : new InstanceResult(rendered);
            });
        });
    }

    private static MapNode parseDefinition(Node node) {
        var modifiers = node.apply("modifiers").flatMap(Attribute::asListOfStrings).orElse(Collections.emptyList());
        var name = node.apply("name").flatMap(Attribute::asString).orElse(JavaString.EMPTY);
        var type = node.apply("type").flatMap(Attribute::asString).orElse(JavaString.EMPTY);
        var value = node.apply("value").flatMap(Attribute::asString).orElse(JavaString.EMPTY);

        JavaString modifierString;
        if (modifiers.isEmpty()) modifierString = JavaString.EMPTY;
        else modifierString = modifiers.stream()
                .filter(modifier -> modifier.equalsToSlice(PUBLIC_KEYWORD))
                .map(modifier -> modifier.concat(new JavaString(" ")))
                .reduce(JavaString::concat)
                .orElse(JavaString.EMPTY);

        var mutabilityString = new JavaString(modifiers.contains(new JavaString(FINAL_KEYWORD))
                ? CONST_KEYWORD_WITH_SPACE
                : LET_KEYWORD_WITH_SPACE);

        return MapNode.Builder(new JavaString("definition"))
                .withString("modifierString", modifierString)
                .withString("mutabilityString", mutabilityString)
                .withString("name", name)
                .withString("outputType", type)
                .withString("after", value)
                .complete();
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
        return new JavaString(renderMagmaImportUnsafe(parent.value(), child.value()));
    }

    private static JavaString renderMagmaDefinition(MapNode node) {
        return new JavaString(renderMagmaDefinitionUnsafe(
                node.apply("modifierString").flatMap(Attribute::asString).orElse(JavaString.EMPTY).value(),
                node.apply("mutabilityString").flatMap(Attribute::asString).orElse(JavaString.EMPTY).value(),
                node.apply("name").flatMap(Attribute::asString).orElse(JavaString.EMPTY).value(),
                node.apply("outputType").flatMap(Attribute::asString).orElse(JavaString.EMPTY).value(),
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
        return modifiersString + CLASS_KEYWORD_WITH_SPACE + name + " " + renderBlock(content);
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
