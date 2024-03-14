package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.meti.None.None;
import static com.meti.Some.Some;

public record Application(Path source) {
    private static Node compileExpression(String line, int indent) {
        var stripped = line.strip();
        return Stream.<Function<String, Option<Node>>>of(stripped3 -> new StringLexer(stripped3).lex(),
                value -> compileField(value, indent),
                stripped2 -> compileBlock(stripped2, indent),
                Application::compileObject,
                Application::compileImport,
                stripped1 -> new InvocationLexer(stripped1).lex(),
                stripped1 -> compileMethod(stripped1, indent)).map(procedure -> procedure.apply(stripped)).filter(Option::isPresent).findFirst().map(optional -> optional.map(Some::Some).orElse(None.None())).orElse(None.None()).map(Application::lexTree).orElse(None::None);
    }

    private static Node lexTree(Node node) {
        var withValue = node.findValueAsNode().flatMap(value -> compileContent(value)
                        .flatMap(node::withValue))
                .orElse(node);

        return withValue.findChildren().flatMap(children -> {
            var newChildren = children.stream()
                    .map(Application::compileContent)
                    .map(output -> output.map(Optional::of).orElse(Optional.empty()))
                    .flatMap(Optional::stream)
                    .collect(Collectors.toList());

            return withValue.withChildren(newChildren);
        }).orElse(withValue);
    }

    private static Option<Node> compileContent(Node content) {
        return content.findValueAsString().and(content.findIndent()).map(tuple -> compileExpression(tuple.a(), tuple.b()));
    }

    private static Option<Node> compileMethod(String stripped, int indent) {
        var paramStart = stripped.indexOf('(');
        var paramEnd = stripped.indexOf(')');
        var contentStart = stripped.indexOf('{');

        if (paramStart != -1 && paramEnd != -1 && contentStart != -1) {
            var keyString = stripped.substring(0, paramStart);
            var space = keyString.lastIndexOf(' ');
            if (space == -1) {
                return None();
            }

            var name = keyString.substring(space + 1).strip();
            var featuresString = keyString.substring(0, space).strip();

            var typeSeparator = featuresString.lastIndexOf(' ');
            var type = compileType(featuresString.substring(typeSeparator + 1).strip());
            var flagsString = Arrays.stream(featuresString.substring(0, typeSeparator).strip().split(" ")).toList();

            List<String> annotations = new ArrayList<>();
            flagsString.forEach(flagString -> {
                if (flagString.startsWith("@")) {
                    annotations.add(compileType(flagString.substring(1)));
                }
            });

            var content = compileExpression(stripped.substring(contentStart).strip(), indent);
            var more = stripped.substring(paramEnd + 1, contentStart).strip();

            var moreOutputValue = more.startsWith("throws ") ? Some(compileType(more.substring("throws ".length()))) : None();

            return Some(new MethodNode(indent, moreOutputValue, annotations, name, type, content));
        }
        return None();
    }

    private static String compileType(String type) {
        if (type.equals("void")) {
            return "Void";
        } else {
            var start = type.indexOf('<');
            var end = type.lastIndexOf('>');
            if (start != -1 && end != -1 && start < end) {
                var name = type.substring(0, start).strip();
                var subType = type.substring(start + 1, end).strip();
                return name + "[" + compileType(subType) + "]";
            } else {
                return type;
            }
        }
    }

    private static Option<Node> compileField(String stripped, int indent) {
        if (stripped.startsWith("public static final Path ")) {
            var content = stripped.substring("public static final Path ".length());
            var equals = content.indexOf('=');
            var name = content.substring(0, equals).strip();
            var compiledValue = new Content(content.substring(equals + 1), 0);
            var flags = List.of("pub", "const");
            return Some(new FieldNode(indent, flags, name, compiledValue));
        }
        return None();
    }

    private static Option<Node> compileBlock(String stripped, int indent) {
        if (stripped.startsWith("{") && stripped.endsWith("}")) {
            var split = split(stripped.substring(1, stripped.length() - 1).strip());
            var stringStream = split.map(line1 -> compileExpression(line1, 1)).collect(Collectors.toList());
            return Some(new BlockNode(indent, stringStream));
        } else {
            return None();
        }
    }

    private static Option<Node> compileObject(String stripped) {
        if (stripped.startsWith("public class ")) {
            var start = stripped.indexOf("{");

            var name = stripped.substring("public class ".length(), start).strip();
            var content = stripped.substring(start);
            var contentOutput = compileExpression(content, 0);

            var flags = List.of("export");
            return Some(new ObjectNode(flags, name, contentOutput));
        }
        return None();
    }

    private static Option<Node> compileImport(String stripped) {
        if (stripped.startsWith("import ")) {
            var isStatic = stripped.startsWith("import static ");
            var content = stripped.substring(isStatic ? "import static ".length() : "import ".length());

            var last = content.lastIndexOf('.');
            var child = content.substring(last + 1).strip();
            var parent = content.substring(0, last).strip();

            return Some(child.equals("*") ? new ImportAllNode(parent) : new ImportChildNode(child, parent));
        }
        return None();
    }

    private static Stream<String> split(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else if (c == '}' && depth == 1) {
                builder.append("}");
                depth--;
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }
        lines.add(builder.toString());
        return lines.stream();
    }

    Option<Path> run() throws IOException {
        if (!Files.exists(source)) return None();

        var input = Files.readString(source);
        var root = split(input)
                .map(line -> compileExpression(line, 0))
                .map(Node::render)
                .map(output -> output.map(Optional::of).orElse(Optional.empty()))
                .flatMap(Optional::stream)
                .collect(Collectors.joining());

        var fileName = source().getFileName().toString();
        var index = fileName.indexOf(".");
        var name = fileName.substring(0, index);
        var target = source().resolveSibling(name + ".mgs");
        Files.writeString(target, root);
        return Some(target);
    }
}