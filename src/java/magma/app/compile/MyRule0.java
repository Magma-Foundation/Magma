package magma.app.compile;

import jvm.api.collect.Lists;
import magma.api.collect.Joiner;
import magma.api.collect.List_;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.rule.MapOutput;
import magma.app.compile.rule.Output;
import magma.app.compile.rule.Rule;

class MyRule0 implements Rule {
    private static Result<Tuple<String, String>, CompileError> compileSegments(ParseState parseState, String input) {
        List_<String> namespace = computeNamespace(input);

        if (isFunctionalImport(namespace)) {
            return Compiler.generateEmpty();
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

        return new Ok<>(new Tuple<>(Compiler.generateImport(stringList), ""));
    }

    private static List_<String> computeNamespace(String left) {
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
        return namespace;
    }

    private static boolean isFunctionalImport(List_<String> namespace) {
        List_<String> slice = namespace.subList(0, 3).orElse(Lists.empty());
        return Lists.equalsTo(slice, Compiler.FUNCTIONAL_NAMESPACE, String::equals);
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

    @Override
    public Result<Output, CompileError> generate(MapNode node) {
        return new Ok<>(new MapOutput().with("header", node.find("header").orElse("")));
    }

    @Override
    public Result<MapNode, CompileError> parse(ParseState state, String input) {
        return apply(state, input).mapValue(tuple -> {
            return new MapNode().withString("header", tuple.left()).withString("target", tuple.right());
        });
    }

    private Result<Tuple<String, String>, CompileError> apply(ParseState parseState, String input) {
        return compileSegments(parseState, input);
    }
}
