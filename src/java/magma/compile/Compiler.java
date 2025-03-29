package magma.compile;

import jvm.collect.list.Lists;
import magma.collect.Joiner;
import magma.collect.list.List_;
import magma.compile.rule.DividingState;
import magma.compile.rule.MutableDividingState;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

public class Compiler {
    public static Result<String, CompileError> compile(String input, List_<String> namespace, String name) {
        return divideStatements(input).stream()
                .foldToResult(new StringBuilder(), (cache, element) -> compileRootSegment(element, namespace).mapValue(cache::append))
                .mapValue(builder -> complete(namespace, name, builder.toString()));
    }

    private static String complete(List_<String> namespace, String name, String output) {
        if (namespace.equals(Lists.of("magma")) && name.equals("Main")) {
            return output + "int main(){\n\treturn 0;\n}\n";
        }

        return output;
    }

    static List_<String> divideStatements(String input) {
        DividingState current = new MutableDividingState();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = divideStatementChar(current, c);
        }

        return current.advance().segments();
    }

    static DividingState divideStatementChar(DividingState current, char c) {
        DividingState appended = current.append(c);
        if (c == ';' && appended.isLevel()) return appended.advance();
        if (c == '{') return appended.enter();
        if (c == '}') return appended.exit();
        return appended;
    }

    private static Result<String, CompileError> compileRootSegment(String input, List_<String> namespace) {
        List_<Rule> rules = Lists.of(
                new Rule() {
                    @Override
                    public Result<String, CompileError> compile(String input) {
                        if (input.startsWith("package ")) return generateEmpty();
                        return createPrefixErr(input, "package ");
                    }
                },
                new Rule() {
                    @Override
                    public Result<String, CompileError> compile(String input) {
                        String stripped = input.strip();
                        if (!stripped.startsWith("import "))
                            return createPrefixErr(stripped, "import ");

                        String right = stripped.substring("import ".length());
                        if (!right.endsWith(";")) return new Err<>(new CompileError("Suffix ';' not present", right));

                        String namespaceString = right.substring(0, right.length() - ";".length());

                        List_<String> requestedNamespace = splitIntoNamespace(namespaceString);

                        if (requestedNamespace.size() >= 3 && requestedNamespace.subList(0, 3).equalsTo(Lists.of("java", "util", "function"))) {
                            return generateEmpty();
                        }

                        String joined = computeNewNamespace(namespace, requestedNamespace)
                                .stream()
                                .collect(new Joiner("/"))
                                .orElse("");

                        return new Ok<>("#include \"" +
                                joined +
                                ".h\"\n");
                    }
                },
                new Rule() {
                    @Override
                    public Result<String, CompileError> compile(String input) {
                        int classIndex = input.indexOf("class ");
                        if (classIndex < 0) return createInfixErr(input, "class ");

                        String afterKeyword = input.substring(classIndex + "class ".length());
                        int contentStart = afterKeyword.indexOf("{");
                        if (contentStart < 0)
                            return new Err<>(new CompileError("Infix '{ ' not present", afterKeyword));

                        String beforeContent = afterKeyword.substring(0, contentStart).strip();
                        int implementsIndex = beforeContent.indexOf(" implements ");
                        String name = implementsIndex >= 0
                                ? beforeContent.substring(0, implementsIndex).strip()
                                : beforeContent;

                        if (name.endsWith(">")) {
                            return generateEmpty();
                        }

                        return generateStruct(new MapNode().withString("name", name));
                    }
                },
                new Rule() {
                    @Override
                    public Result<String, CompileError> compile(String input) {
                        if (input.contains("interface ")) {
                            return generateStruct(new MapNode().withString("name", "Temp"));
                        }
                        return createInfixErr(input, "interface ");
                    }
                },
                new Rule() {
                    @Override
                    public Result<String, CompileError> compile(String input) {
                        if (input.contains("record ")) {
                            return generateStruct(new MapNode().withString("name", "Temp"));
                        }

                        return createInfixErr(input, "record ");
                    }
                }
        );

        return new OrRule(rules).compile(input);
    }

    private static Err<String, CompileError> createInfixErr(String input, String infix) {
        return new Err<>(new CompileError("Infix '" + infix + "' not present", input));
    }

    static Result<String, CompileError> generateStruct(Node node) {
        return new Ok<>("struct " + node.findString("name").orElse("") + " {\n};\n");
    }

    private static Err<String, CompileError> createPrefixErr(String input, String prefix) {
        return new Err<>(new CompileError("Prefix '" + prefix + "' not present", input));
    }

    private static List_<String> splitIntoNamespace(String namespaceString) {
        List_<String> requestedNamespace = Lists.empty();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < namespaceString.length(); i++) {
            char c = namespaceString.charAt(i);
            if (c == '.') {
                requestedNamespace = requestedNamespace.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                buffer.append(c);
            }
        }
        requestedNamespace = requestedNamespace.add(buffer.toString());
        return requestedNamespace;
    }

    private static List_<String> computeNewNamespace(List_<String> thisNamespace, List_<String> requestedNamespace) {
        List_<String> copy = Lists.empty();
        for (int i = 0; i < thisNamespace.size(); i++) {
            copy = copy.add("..");
        }

        copy = copy.addAll(maybeReplacePlatformImport(requestedNamespace));
        return copy;
    }

    private static List_<String> maybeReplacePlatformImport(List_<String> namespace) {
        if (!isNativePlatformImport(namespace)) return namespace;

        return Lists.<String>empty()
                .add("windows")
                .addAll(namespace.subList(1, namespace.size()));
    }

    private static boolean isNativePlatformImport(List_<String> namespace) {
        return namespace.findFirst()
                .filter(value -> value.equals("jvm"))
                .isPresent();
    }

    static Result<String, CompileError> generateEmpty() {
        return new Ok<>("");
    }
}