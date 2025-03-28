package magma;

import jvm.api.collect.Lists;
import magma.api.collect.Joiner;
import magma.api.collect.List_;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.ParseState;

import java.util.function.BiFunction;

public class Compiler {

    public static final List_<String> FUNCTIONAL_NAMESPACE = Lists.of("java", "util", "function");
    public static final String NAME = "name";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";

    static Result<Tuple<String, String>, CompileError> compile(ParseState parseState, String input) {
        return compileRoot(input, parseState).mapValue(tuple -> {
            return new Tuple<String, String>(wrapHeader(tuple, parseState), wrapTarget(parseState, tuple));
        });
    }

    private static String wrapHeader(Tuple<String, String> tuple, ParseState parseState) {
        String path = parseState.namespace().add(parseState.name())
                .stream()
                .collect(new Joiner("_"))
                .orElse("");

        return generateDirectiveWithValue("ifndef", path) +
                generateDirectiveWithValue("define", path) +
                tuple.left() +
                generateDirective("endif");
    }

    private static String generateDirective(String name) {
        return generateDirectiveWithSuffix(name, "");
    }

    private static String generateDirectiveWithValue(String name, String value) {
        return generateDirectiveWithSuffix(name, " " + value);
    }

    private static String generateDirectiveWithSuffix(String name, String suffix) {
        return "#" + name + suffix + "\n";
    }

    static String generateImport(String content) {
        return "#include \"" + content + ".h" + "\"\n";
    }

    static Result<Tuple<String, String>, CompileError> compileRoot(String input, ParseState parseState) {
        return divideAndCompile(input, parseState, Compiler::compileRootSegment);
    }

    static Result<Tuple<String, String>, CompileError> divideAndCompile(String input, ParseState parseState, BiFunction<String, ParseState, Result<Tuple<String, String>, CompileError>> compiler) {
        List_<Character> queue = Lists.fromString(input);

        DivideState current = new DivideState(queue);
        while (true) {
            Option<Tuple<DivideState, Character>> maybeNext = current.pop();
            if (maybeNext.isEmpty()) break;

            Tuple<DivideState, Character> tuple1 = maybeNext.orElse(new Tuple<>(current, '\0'));
            current = divideText(tuple1.left(), tuple1.right());
        }

        return current.advance().stream()
                .foldToResult(new Tuple<>(new StringBuilder(), new StringBuilder()), (output, segment) -> compiler.apply(segment, parseState).mapValue(result -> appendBuilders(output, result)))
                .mapValue(tuple -> new Tuple<>(tuple.left().toString(), tuple.right().toString()));
    }

    private static DivideState divideText(DivideState state, char c) {
        DivideState appended = state.appendChar(c);

        return divideSingleQuotes(appended, c)
                .orElseGet(() -> divideStatementChar(appended, c));
    }

    private static Option<DivideState> divideSingleQuotes(DivideState state, char c) {
        if (c != '\'') return new None<>();

        return state.append().flatMap(maybeSlash -> {
            Option<DivideState> divideStateOption;
            DivideState oldState = maybeSlash.left();
            if (maybeSlash.right() == '\\') {
                divideStateOption = oldState.appendAndDiscard();
            } else {
                divideStateOption = new Some<>(oldState);
            }

            return divideStateOption.flatMap(DivideState::appendAndDiscard);
        });
    }

    private static DivideState divideStatementChar(DivideState appended, char c) {
        if (c == ';' && appended.isLevel()) return appended.advance();
        if (c == '}' && appended.isShallow()) return appended.advance().exit();
        if (c == '{') return appended.enter();
        if (c == '}') return appended.exit();
        return appended;
    }

    static Tuple<StringBuilder, StringBuilder> appendBuilders(Tuple<StringBuilder, StringBuilder> builders, Tuple<String, String> elements) {
        StringBuilder newLeft = builders.left().append(elements.left());
        StringBuilder newRight = builders.right().append(elements.right());
        return new Tuple<>(newLeft, newRight);
    }

    static Result<Tuple<String, String>, CompileError> compileRootSegment(String input, ParseState parseState) {
        String stripped = input.strip();
        if (stripped.isEmpty()) return generateEmpty();
        if (input.startsWith("package ")) return generateEmpty();

        if (stripped.startsWith("import ")) {
            String right = stripped.substring("import ".length());
            if (right.endsWith(";")) {
                String left = right.substring(0, right.length() - ";".length());

                List_<String> namespace = Lists.empty();
                StringBuilder buffer = new StringBuilder();
                for (int i = 0; i < left.length(); i++) {
                    char c = left.charAt(i);
                    if (c == '.') {
                        namespace = namespace.add(buffer.toString());
                        buffer = new StringBuilder();
                    } else {
                        buffer.append(c);
                    }
                }
                namespace = namespace.add(buffer.toString());

                if (isFunctionalImport(namespace)) {
                    return generateEmpty();
                }

                List_<String> copy = Lists.empty();
                List_<String> thisNamespace = parseState.namespace();

                for (int i = 0; i < thisNamespace.size(); i++) {
                    copy = copy.add("..");
                }

                if (namespace.isEmpty()) copy.add(".");

                List_<String> finalCopy = copy;
                List_<String> withNamespace = finalCopy.addAll(namespace);

                List_<String> mapped = namespace.popFirst()
                        .map(tuple -> mapPlatformDependentNamespace(tuple.left(), tuple.right(), finalCopy).orElse(withNamespace))
                        .orElse(withNamespace);

                String stringList = mapped
                        .stream()
                        .collect(new Joiner("/"))
                        .orElse("");

                return new Ok<>(new Tuple<>(generateImport(stringList), ""));
            }
        }

        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String right = input.substring(classIndex + "class ".length());
            int contentStart = right.indexOf("{");
            if (contentStart >= 0) {
                String name = right.substring(0, contentStart).strip();
                String withEnd = right.substring(contentStart + "{".length()).strip();

                int implementsIndex = name.indexOf(" implements ");
                String name1 = implementsIndex >= 0
                        ? name.substring(0, implementsIndex).strip()
                        : name;

                if (name1.endsWith(">")) {
                    return generateEmpty();
                }

                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    return divideAndCompile(inputContent, parseState, (s, parseState2) -> compileClassMember(s))
                            .flatMapValue(tuple -> generateStruct(new MapNode()
                                    .withString(NAME, name1)
                                    .withString(LEFT, tuple.left())
                                    .withString(RIGHT, tuple.right())));
                }
            }
        }

        int recordIndex = input.indexOf("record ");
        if (recordIndex >= 0) {
            String right = input.substring(recordIndex + "record ".length());
            int contentStart = right.indexOf("{");
            if (contentStart >= 0) {
                String beforeContent = right.substring(0, contentStart).strip();
                String withEnd = right.substring(contentStart + "{".length()).strip();

                int paramStart = beforeContent.indexOf("(");
                if (paramStart >= 0) {
                    String maybeWithTypeParams = beforeContent.substring(0, paramStart).strip();
                    int implementsIndex = maybeWithTypeParams.indexOf(" implements ");
                    String name = implementsIndex >= 0
                            ? maybeWithTypeParams.substring(0, implementsIndex).strip()
                            : maybeWithTypeParams;

                    if (name.endsWith(">")) return generateEmpty();
                    if (withEnd.endsWith("}")) {
                        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                        return divideAndCompile(inputContent, parseState, (s, parseState1) -> compileClassMember(s)).flatMapValue(content -> {
                            return generateStruct(new MapNode()
                                    .withString(NAME, name)
                                    .merge(new MapNode()
                                            .withString(LEFT, content.left())
                                            .withString(RIGHT, content.right())));
                        });
                    }
                }
            }
        }

        int interfaceIndex = input.indexOf("interface ");
        if (interfaceIndex >= 0) {
            String right = input.substring(interfaceIndex + "interface ".length());
            int contentStart = right.indexOf("{");
            if (contentStart >= 0) {
                String beforeContent = right.substring(0, contentStart).strip();
                if (beforeContent.endsWith(">")) {
                    return generateEmpty();
                }

                String name;
                int extendsKeyword = beforeContent.indexOf(" extends ");
                if (extendsKeyword >= 0) {
                    name = beforeContent.substring(0, extendsKeyword).strip();
                } else {
                    name = beforeContent;
                }

                final Tuple<String, String> content = new Tuple<>("", "");
                MapNode other = new MapNode()
                        .withString(LEFT, content.left())
                        .withString(RIGHT, content.right());

                return generateStruct(new MapNode()
                        .withString(NAME, name)
                        .merge(other));
            }
        }

        return new Err<>(new CompileError("Invalid root segment", input));
    }

    private static Result<Tuple<String, String>, CompileError> compileClassMember(String input) {
        if (input.isBlank()) return generateEmpty();

        if (input.endsWith(";")) return new Ok<>(new Tuple<>("\tint value;\n", ""));

        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String definition = input.substring(0, paramStart).strip();
            int nameSeparator = definition.lastIndexOf(" ");
            if (nameSeparator >= 0) {
                String oldName = definition.substring(nameSeparator + " ".length()).strip();
                String newName = oldName.equals("main") ? "__main__" : oldName;
                return new Ok<>(new Tuple<>("", "void " + newName + "(){\n}\n"));
            }
        }

        return new Err<>(new CompileError("Invalid class segment", input));
    }

    private static boolean isFunctionalImport(List_<String> namespace) {
        List_<String> slice = namespace.subList(0, 3).orElse(Lists.empty());
        return Lists.equalsTo(slice, FUNCTIONAL_NAMESPACE, String::equals);
    }

    private static Option<List_<String>> mapPlatformDependentNamespace(
            String first,
            List_<String> slice,
            List_<String> copy
    ) {
        return first.equals("jvm")
                ? new Some<>(copy.add("windows").addAll(slice))
                : new None<>();
    }

    static Ok<Tuple<String, String>, CompileError> generateEmpty() {
        return new Ok<>(new Tuple<>("", ""));
    }

    static Result<Tuple<String, String>, CompileError> generateStruct(MapNode node) {
        String name = node.find(NAME).orElse("");
        String left = node.find(LEFT).orElse("");
        String right = node.find(RIGHT).orElse("");

        return new Ok<>(new Tuple<>("struct " + name + " {\n};\n" + left, right));
    }

    public static String wrapTarget(ParseState parseState, Tuple<String, String> tuple) {
        String name = parseState.name();
        String source = generateImport(name) + tuple.right();
        if (Lists.equalsTo(parseState.namespace(), Lists.of("magma"), String::equals) && name.equals("Main")) {
            return source + "int main(){\n\treturn 0;\n}\n";
        } else {
            return source;
        }
    }
}