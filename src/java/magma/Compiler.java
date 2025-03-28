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
import magma.app.compile.State;

public class Compiler {

    public static final List_<String> FUNCTIONAL_NAMESPACE = Lists.of("java", "util", "function");

    static Result<Tuple<String, String>, CompileError> compile(State state, String input) {
        return divideAndCompile(input, state).mapValue(tuple -> {
            return new Tuple<String, String>(wrapHeader(tuple, state), wrapTarget(state, tuple));
        });
    }

    private static String wrapHeader(Tuple<String, String> tuple, State state) {
        String path = state.namespace().add(state.name())
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

    static Result<Tuple<String, String>, CompileError> divideAndCompile(String input, State state) {
        List_<String> segments = Lists.empty();
        StringBuilder buffer = new StringBuilder();
        int depth = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            buffer.append(c);
            if (c == ';' && depth == 0) {
                segments = segments.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
            }
        }
        segments = segments.add(buffer.toString());

        return segments.stream()
                .foldToResult(new Tuple<>(new StringBuilder(), new StringBuilder()), (output, segment) -> compileRootSegment(segment, state).mapValue(result -> appendBuilders(output, result)))
                .mapValue(tuple -> new Tuple<>(tuple.left().toString(), tuple.right().toString()));
    }

    static Tuple<StringBuilder, StringBuilder> appendBuilders(Tuple<StringBuilder, StringBuilder> builders, Tuple<String, String> elements) {
        StringBuilder newLeft = builders.left().append(elements.left());
        StringBuilder newRight = builders.right().append(elements.right());
        return new Tuple<StringBuilder, StringBuilder>(newLeft, newRight);
    }

    static Result<Tuple<String, String>, CompileError> compileRootSegment(String input, State state) {
        if (input.startsWith("package ")) return generateEmpty();

        if (input.strip().startsWith("import ")) {
            String right = input.strip().substring("import ".length());
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
                List_<String> thisNamespace = state.namespace();

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
                if (name.endsWith(">")) {
                    return generateEmpty();
                }
                return generateStruct(name);
            }
        }

        int recordIndex = input.indexOf("record ");
        if (recordIndex >= 0) {
            String right = input.substring(recordIndex + "record ".length());
            int contentStart = right.indexOf("{");
            if (contentStart >= 0) {
                String beforeContent = right.substring(0, contentStart).strip();
                int paramStart = beforeContent.indexOf("(");
                if (paramStart >= 0) {
                    String maybeWithTypeParams = beforeContent.substring(0, paramStart).strip();
                    if (maybeWithTypeParams.endsWith(">")) {
                        return generateEmpty();
                    } else {
                        return generateStruct(maybeWithTypeParams);
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

                return generateStruct(name);
            }
        }

        return new Err<>(new CompileError("Invalid root segment", input));
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

    static Ok<Tuple<String, String>, CompileError> generateStruct(String name) {
        return new Ok<>(new Tuple<>("struct " + name + " {\n};\n", ""));
    }

    public static String wrapTarget(State state, Tuple<String, String> tuple) {
        String name = state.name();
        String source = generateImport(name) + tuple.right();
        if (Lists.equalsTo(state.namespace(), Lists.of("magma"), String::equals) && name.equals("Main")) {
            return source + "int main(){\n\treturn 0;\n}\n";
        } else {
            return source;
        }
    }
}