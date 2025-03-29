package magma.compile;

import jvm.collect.list.Lists;
import magma.collect.Joiner;
import magma.collect.list.List_;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;
import magma.result.Results;

public class Compiler {
    public static String compile(String input, List_<String> namespace, String name) throws CompileException {
        StringBuilder builder = Results.unwrap(divideStatements(input).stream()
                .foldToResult(new StringBuilder(), (cache, element) -> compileRootSegment(element, namespace).mapValue(cache::append)));

        String output = builder.toString();
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

    private static Result<String, CompileException> compileRootSegment(String input, List_<String> namespace) {
        List_<Rule> rules = Lists.of(
                new Rule() {
                    @Override
                    public Result<String, CompileException> compile(String input) {
                        return compilePackage(input)
                                .<Result<String, CompileException>>map(Ok::new)
                                .orElseGet(() -> new Err<>(new CompileException("No value present", input)));
                    }
                },
                new Rule() {
                    @Override
                    public Result<String, CompileException> compile(String input) {
                        return compileImport(input, namespace)
                                .<Result<String, CompileException>>map(Ok::new)
                                .orElseGet(() -> new Err<>(new CompileException("No value present", input)));
                    }
                },
                new Rule() {
                    @Override
                    public Result<String, CompileException> compile(String input) {
                        return compileClass(input)
                                .<Result<String, CompileException>>map(Ok::new)
                                .orElseGet(() -> new Err<>(new CompileException("No value present", input)));
                    }
                },
                new Rule() {
                    @Override
                    public Result<String, CompileException> compile(String input) {
                        return compileInterface(input)
                                .<Result<String, CompileException>>map(Ok::new)
                                .orElseGet(() -> new Err<>(new CompileException("No value present", input)));
                    }
                },
                new Rule() {
                    @Override
                    public Result<String, CompileException> compile(String input) {
                        return compileRecord(input)
                                .<Result<String, CompileException>>map(Ok::new)
                                .orElseGet(() -> new Err<>(new CompileException("No value present", input)));
                    }

                    private Option<String> compileRecord(String input2) {
                        if (input2.contains("record ")) {
                            return generateStruct();
                        }

                        return new None<>();
                    }
                }
        );

        return new OrRule(rules).compile(input);
    }

    static Option<String> compilePackage(String input) {
        if (input.startsWith("package ")) return generateEmpty();
        return new None<String>();
    }

    static Option<String> compileClass(String input) {
        if (input.contains("class ")) {
            return generateStruct();
        }
        return new None<String>();
    }

    static Option<String> generateStruct() {
        return new Some<String>("struct Temp {\n};\n");
    }

    static Option<String> compileInterface(String input) {
        if (input.contains("interface ")) {
            return generateStruct();
        }
        return new None<String>();
    }

    static Option<String> compileImport(String input, List_<String> thisNamespace) {
        if (!input.strip().startsWith("import ")) return new None<String>();

        String right = input.strip().substring("import ".length());
        if (!right.endsWith(";")) return new None<>();

        String namespaceString = right.substring(0, right.length() - ";".length());

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

        if (requestedNamespace.size() >= 3 && requestedNamespace.subList(0, 3).equalsTo(Lists.of("java", "util", "function"))) {
            return generateEmpty();
        }

        String joined = computeNewNamespace(thisNamespace, requestedNamespace)
                .stream()
                .collect(new Joiner("/"))
                .orElse("");

        return new Some<String>("#include \"" +
                joined +
                ".h\"\n");
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

    static Some<String> generateEmpty() {
        return new Some<String>("");
    }
}