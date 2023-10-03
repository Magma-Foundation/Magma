package com.meti.compile;

import com.meti.api.collect.Collectors;
import com.meti.api.collect.ImmutableLists;
import com.meti.api.collect.JavaString;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.api.option.ThrowableOption;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;
import com.meti.api.result.Results;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.node.MapNode;
import com.meti.compile.node.Node;
import com.meti.compile.state.Cache;
import com.meti.compile.state.Splitter;
import com.meti.compile.state.State;

import static com.meti.api.result.Results.$Result;

public record Compiler(JavaString input) {

    private static Result<Node, CompileException> lex(JavaString input) {
        return $Result(() -> {
            if (input.isBlank()) {
                throw new CompileException("Input cannot be empty.");
            }

            return new JavaLexer(input)
                    .lex()
                    .into(ThrowableOption::new)
                    .unwrapOrThrow(new CompileException("Invalid input: '%s'.".formatted(input)))
                    .$();
        });
    }

    private static Result<JavaString, CompileException> renderNode(Node node) {
        return new MagmaRenderer(node).render()
                .into(ThrowableOption::new)
                .unwrapOrThrow(new CompileException("Cannot render: " + node.toXML().value()))
                .flatMapValue(value -> value);
    }

    private static Result<State, CompileException> transform(Node node) {
        return $Result(() -> {
            if (node.is("package")) {
                return new DiscardState();
            }

            var actualNode = removeClass(node)
                    .orElseGet(() -> removeRecord(node))
                    .orElseGet(() -> removeInterface(node))
                    .orElseGet(() -> removeMethod(node))
                    .unwrapOrElse(Ok.apply(node))
                    .$();

            return new ContinueState(actualNode);
        });
    }

    private static Option<Result<Node, CompileException>> removeRecord(Node node) {
        if (node.is("record")) {
            return Some.apply($Result(() -> {
                var name = node.apply("name")
                        .flatMap(Attribute::asString)
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new NodeException("No name present.", node))
                        .$();

                var body = node.apply("body")
                        .flatMap(Attribute::asNode)
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new NodeException("No body present.", node))
                        .$();

                return MapNode.Builder("function")
                        .withString("name", name)
                        .withNodeList("parameters", ImmutableLists.empty())
                        .withNode("body", body)
                        .complete();
            }));
        } else {
            return None.apply();
        }
    }

    private static Option<Result<Node, CompileException>> removeMethod(Node node) {
        if (node.is("method")) {
            return Some.apply($Result(() -> {
                var name = node.apply("name").flatMap(Attribute::asString)
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new NodeException("No name present.", node))
                        .$();

                var parameters = node.apply("parameters")
                        .flatMap(Attribute::asListOfNodes)
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new NodeException("No parameters present.", node))
                        .$();

                return MapNode.Builder("function")
                        .withString("name", name)
                        .withNodeList("parameters", parameters)
                        .complete();
            }));
        } else {
            return None.apply();
        }
    }

    private static Option<Result<Node, CompileException>> removeInterface(Node node) {
        if (node.is("interface")) {
            return Some.apply(node.apply("name")
                    .flatMap(Attribute::asString)
                    .map(value -> MapNode.Builder("struct").withString("name", value).complete())
                    .into(ThrowableOption::new)
                    .unwrapOrThrow(new NodeException("No name present.", node)));
        } else {
            return None.apply();
        }
    }

    private static Option<Result<Node, CompileException>> removeClass(Node node) {
        if (node.is("class")) {
            return Some.apply(Results.$Result(() -> {
                var name = node.apply("name")
                        .flatMap(Attribute::asString)
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new NodeException("No name present.", node))
                        .$();

                var body = node.apply("body")
                        .flatMap(Attribute::asNode)
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new NodeException("No body present.", node))
                        .$();

                return MapNode.Builder("function")
                        .withString("name", name)
                        .withNodeList("parameters", ImmutableLists.empty())
                        .withNode("body", body)
                        .complete();
            }));
        } else {
            return None.apply();
        }
    }

    public Result<JavaString, CompileException> compile() {
        return $Result(() -> {
            var lines = new Splitter(input()).split();
            var lexed = lines.iter()
                    .map(JavaString::strip)
                    .map(Compiler::lex)
                    .collect(Collectors.exceptionally(ImmutableLists.into()))
                    .$();

            var cache = lexed.iter().foldRight(Ok.<Cache, CompileException>apply(new Cache()), (cacheResult, node) -> cacheResult.flatMapValue(tempCache -> $Result(() -> {
                var state = transform(node).$();
                return tempCache.add(state);
            }))).$();

            return cache.values()
                    .iter()
                    .map(Compiler::renderNode)
                    .collect(Collectors.exceptionally(Collectors.joining())).$()
                    .unwrapOrElse(JavaString.Empty);
        });
    }

    record ContinueState(Node node) implements State {
        @Override
        public Option<Node> value() {
            return Some.apply(node);
        }
    }

    record DiscardState() implements State {
        @Override
        public Option<Node> value() {
            return None.apply();
        }
    }
}