package com.meti.compile;

import com.meti.CompileException;
import com.meti.api.collect.JavaList;
import com.meti.api.collect.JavaMap;
import com.meti.api.collect.List;
import com.meti.api.iterator.ArrayIterator;
import com.meti.api.iterator.Collectors;
import com.meti.api.iterator.Iterators;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;
import com.meti.api.result.ThrowableOption;
import com.meti.compile.rule.ConjunctionRule;
import com.meti.compile.rule.EqualRule;
import com.meti.compile.rule.Rule;
import com.meti.compile.rule.ValueRule;

import java.util.Map;
import java.util.function.BiFunction;

import static com.meti.api.result.Results.$Result;

public record Compiler(String input) {

    private static Result<String, CompileException> render(Node node) {
        var map = new JavaMap<>(Map.of(
                "import", ConjunctionRule.join(
                        new EqualRule("import { "),
                        new ValueRule("child"),
                        new EqualRule(" } from "),
                        new ValueRule("parent"),
                        new EqualRule(";")),
                "function", ConjunctionRule.join(
                        new EqualRule("class def "),
                        new ValueRule("name"),
                        new EqualRule("() => {}"))
        ));

        return map.iter()
                .map(entry -> node.is(entry.a()) ? entry.b().toString(node) : None.<String>apply())
                .flatMap(Iterators::ofOption).head()
                .into(ThrowableOption::new)
                .unwrapOrThrow(new CompileException("Cannot render nodes."));
    }

    private static Option<Node> lexByRule(String type, Rule rule, String line) {
        return rule.fromString(line)
                .flatMap(List::head)
                .map(evaluated -> new ResultNode(type, evaluated));
    }

    private static Option<Node> transformNode(Node node) {
        if (node.is("package")) {
            return None.apply();
        } else if (node.is("record")) {
            return Some.apply(new MapNode("function", new JavaMap<>(Map.of(
                    "name", node.getString("name").unwrapOrElse("")
            ))));
        } else {
            return Some.apply(node);
        }
    }

    private static Result<String, CompileException> renderNodes(List<Node> list) {
        return list.iter()
                .map(Compiler::render)
                .collect(Collectors.exceptionally(Collectors.joining()));
    }

    public String compile() throws CompileException {
        var args = input.split(";");
        return new ArrayIterator<>(args)
                .filter(line -> !line.isEmpty())
                .map(this::lexTree)
                .collect(Collectors.exceptionally(JavaList.collect()))
                .mapValue(nodes -> nodes.iter()
                        .map(Compiler::transformNode)
                        .flatMap(Iterators::ofOption)
                        .collect(JavaList.collect()))
                .mapValueExceptionally(Compiler::renderNodes)
                .unwrap();
    }

    private Result<Node, CompileException> lexTree(String line1) {
        return $Result(() -> {
            var root = lexLine(line1).$();
            var nodeKeys = root.getNodes().foldRight(Ok.apply(root), (BiFunction<Result<Node, CompileException>, Tuple<String, Node>, Result<Node, CompileException>>) (nodeCompileExceptionResult, stringNodeTuple) -> $Result(() -> {
                var root1 = nodeCompileExceptionResult.$();
                var key = stringNodeTuple.a();
                var value = stringNodeTuple.b();
                var textValue = value.getValue()
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new CompileException("No value present."))
                        .$();
                var newValue = lexLine(textValue).$();
                return root1.withNode(key, newValue)
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new CompileException("Original node did not have required property."))
                        .$();
            })).$();
            return nodeKeys;
        });
    }

    private Result<Node, CompileException> lexLine(String line) {
        var map = new JavaMap<>(Map.of(
                "package", ConjunctionRule.join(
                        new EqualRule("package "),
                        new ValueRule("value")
                ),
                "import", ConjunctionRule.join(
                        new EqualRule("import "),
                        new ValueRule("parent"),
                        new EqualRule("."),
                        new ValueRule("child")
                ),
                "record", ConjunctionRule.join(
                        new EqualRule("record "),
                        new ValueRule("name"),
                        new EqualRule("(){}"))));

        return map.iter()
                .map(entry -> lexByRule(entry.a(), entry.b(), line))
                .flatMap(Iterators::ofOption)
                .head()
                .into(ThrowableOption::new)
                .unwrapOrThrow(new CompileException("Unknown line: '" + line + "'"));
    }
}