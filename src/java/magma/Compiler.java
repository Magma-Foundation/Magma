package magma;

import jvm.api.collect.Lists;
import magma.api.collect.Joiner;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Compiler {
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
        List<String> segments = new ArrayList<String>();
        StringBuilder buffer = new StringBuilder();
        int depth = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            buffer.append(c);
            if (c == ';' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
            }
        }
        segments.add(buffer.toString());

        Result<Tuple<StringBuilder, StringBuilder>, CompileError> output = new Ok<Tuple<StringBuilder, StringBuilder>, CompileError>(new Tuple<StringBuilder, StringBuilder>(new StringBuilder(), new StringBuilder()));
        for (String segment : segments) {
            output = output
                    .and(() -> compileRootSegment(segment, state))
                    .mapValue(Compiler::appendBuilders);
        }

        return output.mapValue(tuple -> new Tuple<>(tuple.left().toString(), tuple.right().toString()));
    }

    static Tuple<StringBuilder, StringBuilder> appendBuilders(Tuple<Tuple<StringBuilder, StringBuilder>, Tuple<String, String>> tuple) {
        Tuple<StringBuilder, StringBuilder> builders = tuple.left();
        Tuple<String, String> elements = tuple.right();
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
                List<String> namespace = new ArrayList<String>(Arrays.asList(left.split(Pattern.quote("."))));
                if (namespace.size() >= 3 && namespace.subList(0, 3).equals(List.of("java", "util", "function"))) {
                    return generateEmpty();
                }

                List<String> copy = new ArrayList<String>();

                List<String> thisNamespace = Lists.toNative(state.namespace());
                for (int i = 0; i < thisNamespace.size(); i++) {
                    copy.add("..");
                }

                if (namespace.isEmpty()) copy.add(".");

                if (!namespace.isEmpty()) {
                    String oldFirst = namespace.getFirst();
                    if (oldFirst.equals("jvm")) {
                        copy.add("windows");
                        copy.addAll(namespace.subList(1, namespace.size()));
                    } else {
                        copy.addAll(namespace);
                    }
                }

                String joined = String.join("/", copy);
                return new Ok<>(new Tuple<>(generateImport(joined), ""));
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

        return new Err<Tuple<String, String>, CompileError>(new CompileError("Invalid root segment", input));
    }

    static Ok<Tuple<String, String>, CompileError> generateEmpty() {
        return new Ok<Tuple<String, String>, CompileError>(new Tuple<String, String>("", ""));
    }

    static Ok<Tuple<String, String>, CompileError> generateStruct(String name) {
        return new Ok<Tuple<String, String>, CompileError>(new Tuple<String, String>("struct " + name + " {\n};\n", ""));
    }

    public static String wrapTarget(State state, Tuple<String, String> tuple) {
        String name = state.name();
        String source = generateImport(name) + tuple.right();
        if (Lists.toNative(state.namespace()).equals(List.of("magma")) && name.equals("Main")) {
            return source + "int main(){\n\treturn 0;\n}\n";
        } else {
            return source;
        }
    }
}