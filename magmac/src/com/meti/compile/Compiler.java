package com.meti.compile;

import com.meti.api.Tuple;
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
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodeListAttribute;
import com.meti.compile.node.Content;
import com.meti.compile.node.MapNode;
import com.meti.compile.node.Node;
import com.meti.compile.state.Cache;
import com.meti.compile.state.Splitter;
import com.meti.compile.state.State;

import java.util.function.BiFunction;

import static com.meti.api.result.Results.$Result;

public record Compiler(JavaString input, LexerFactory<Node> factory) {

    private Result<Node, CompileException> lexTree(JavaString input, JavaString type) {
        return $Result(() -> {
            var root = lexNode(input, type).$();
            var withNodes = root.stream(Attribute.Group.Node).foldRight(Ok.apply(root), (BiFunction<Result<Node, CompileException>, Tuple<JavaString, Attribute>, Result<Node, CompileException>>) (nodeCompileExceptionResult, javaStringAttributeTuple) -> $Result(() -> {
                var node11 = nodeCompileExceptionResult.$();
                var key = javaStringAttributeTuple.a();
                var node1 = javaStringAttributeTuple.b()
                        .asNode()
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new CompileException(key.append(" was not a node.").value()))
                        .$();

                var tuple = extractValue(node1).$();
                var value = new NodeAttribute(lexNode(tuple.a(), tuple.b()).$());

                return node11.with(key, value)
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new CompileException("New value was not replaced."))
                        .$();
            })).$();

            return withNodes.stream(Attribute.Group.NodeList).foldRight(Ok.apply(withNodes), (BiFunction<Result<Node, CompileException>, Tuple<JavaString, Attribute>, Result<Node, CompileException>>) (nodeCompileExceptionResult, javaStringAttributeTuple) -> $Result(() -> {
                var node = nodeCompileExceptionResult.$();
                var key = javaStringAttributeTuple.a();
                var value = javaStringAttributeTuple.b()
                        .asListOfNodes()
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new CompileException("Not a list of nodes."))
                        .$();

                var list = value.iter()
                        .map(value1 -> extractValue(value1).flatMapValue((Tuple<JavaString, JavaString> input1) -> lexTree(input1.a(), input1.b())))
                        .collect(Collectors.exceptionally(ImmutableLists.into()))
                        .$();

                return node.with(key, new NodeListAttribute(list))
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new CompileException("Could not attach list."))
                        .$();
            })).$();
        });
    }

    private static Result<Tuple<JavaString, JavaString>, CompileException> extractValue(Node value1) {
        return $Result(() -> {
            var value = value1.apply("value")
                    .flatMap(Attribute::asString)
                    .into(ThrowableOption::new)
                    .unwrapOrThrow(new CompileException("No value present"))
                    .$();

            var type = value1.apply("type")
                    .flatMap(Attribute::asString)
                    .into(ThrowableOption::new)
                    .unwrapOrThrow(new CompileException("No value present"))
                    .$();

            return new Tuple<>(value, type);
        });
    }

    private  Result<Node, CompileException> lexNode(JavaString input, JavaString type) {
        return $Result(() -> {
            if (input.isBlank()) {
                throw new CompileException("Input cannot be empty.");
            }

            return factory.create(input, type)
                    .mapErr(CompileException::new)
                    .$()
                    .lex()
                    .into(ThrowableOption::new)
                    .unwrapOrThrow(new CompileException("For type '%s', invalid input value '%s'.".formatted(type, input)))
                    .$();
        });
    }

    private static Result<JavaString, CompileException> renderTree(Node node) {
        return $Result(() -> {
            var renderedTree = node.stream(Attribute.Group.Node).foldRight(Ok.apply(node), (BiFunction<Result<Node, CompileException>, Tuple<JavaString, Attribute>, Result<Node, CompileException>>) (nodeCompileExceptionResult, tuple) -> $Result(() -> {
                var root = nodeCompileExceptionResult.$();
                var key = tuple.a();
                var value = tuple.b();
                var node1 = value.asNode()
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new CompileException("Not a node."))
                        .$();

                var rendered = renderNode(node1).$();
                return root.with(key, new NodeAttribute(new Content(rendered, JavaString.Empty)))
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new CompileException("Did not add attribute."))
                        .$();
            })).$();
            return renderNode(renderedTree).$();
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
            return Some.apply($Result(() -> MapNode.Builder("function")
                    .with(node)
                    .complete()));
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
                    .map(input1 -> lexTree(input1, JavaString.apply("any")))
                    .collect(Collectors.exceptionally(ImmutableLists.into()))
                    .$();

            var cache = lexed.iter().foldRight(Ok.<Cache, CompileException>apply(new Cache()), (cacheResult, node) -> cacheResult.flatMapValue(tempCache -> $Result(() -> {
                var state = transform(node).$();
                return tempCache.add(state);
            }))).$();

            return cache.values()
                    .iter()
                    .map(Compiler::renderTree)
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