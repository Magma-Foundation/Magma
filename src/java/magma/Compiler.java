package magma;

import jvm.collect.list.Lists;
import jvm.option.Options;
import magma.collect.Joiner;
import magma.collect.list.List_;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class Compiler {
    static String compile(String input, List<String> namespace, String name) throws CompileException {
        List<String> segments = divideStatements(input);

        StringBuilder builder = new StringBuilder();
        for (String segment : segments) {
            builder.append(compileRootSegment(segment, namespace));
        }

        String output = builder.toString();
        if (namespace.equals(List.of("magma")) && name.equals("Main")) {
            return output + "int main(){\n\treturn 0;\n}\n";
        }

        return output;
    }

    static List<String> divideStatements(String input) {
        DividingState current = new MutableDividingState();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = divideStatementChar(current, c);
        }

        return Lists.toNative(current.advance().segments());
    }

    static DividingState divideStatementChar(DividingState current, char c) {
        DividingState appended = current.append(c);
        if (c == ';' && appended.isLevel()) return appended.advance();
        if (c == '{') return appended.enter();
        if (c == '}') return appended.exit();
        return appended;
    }

    static String compileRootSegment(String input, List<String> namespace) throws CompileException {
        List<Rule> rules = List.of(
                new Rule() {
                    @Override
                    public Option<String> compile(String input) {
                        return compilePackage(input);
                    }
                },
                new Rule() {
                    @Override
                    public Option<String> compile(String input) {
                        return compileImport(input, namespace);
                    }
                },
                new Rule() {
                    @Override
                    public Option<String> compile(String input) {
                        return compileClass(input);
                    }
                },
                new Rule() {
                    @Override
                    public Option<String> compile(String input) {
                        return compileInterface(input);
                    }

                },
                new Rule() {
                    @Override
                    public Option<String> compile(String input) {
                        if (input.contains("record ")) {
                            return generateStruct();
                        }

                        return new None<String>();
                    }
                }
        );

        for (Rule rule : rules) {
            Optional<String> maybe = Options.toNative(rule.compile(input));
            if (maybe.isPresent()) return maybe.get();
        }

        throw new CompileException("Invalid root segment", input);
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

    static Option<String> compileImport(String input, List<String> thisNamespace) {
        if (!input.strip().startsWith("import ")) return new None<String>();

        String right = input.strip().substring("import ".length());
        if (!right.endsWith(";")) return new None<String>();

        String namespaceString = right.substring(0, right.length() - ";".length());
        List_<String> requestedNamespace = Lists.fromArray(namespaceString.split(Pattern.quote(".")));
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

    private static List_<String> computeNewNamespace(List<String> thisNamespace, List_<String> requestedNamespace) {
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