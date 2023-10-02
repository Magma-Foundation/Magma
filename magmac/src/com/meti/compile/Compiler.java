package com.meti.compile;

import com.meti.api.collect.Collectors;
import com.meti.api.collect.ImmutableLists;
import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.api.option.ThrowableOption;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;
import com.meti.compile.block.BlockNode;

import static com.meti.api.result.Results.$Result;

public record Compiler(JavaString input) {

    static Result<JavaString, CompileException> compileLine(JavaString input) {
        return $Result(() -> {
            if(input.isBlank()) {
                throw new CompileException("Input cannot be empty.");
            }

            var lexed = new JavaLexer(input)
                    .lex()
                    .into(ThrowableOption::new)
                    .unwrapOrThrow(new CompileException("Invalid input: '%s'.".formatted(input)))
                    .$();

            var state = transform(lexed).$()
                    .value()
                    .unwrapOrElse(lexed);

            return renderNode(state);
        });
    }

    private static JavaString renderNode(Node node) {
        return new MagmaRenderer(node).render().unwrapOrElseGet(() -> JavaString.Empty);
    }

    private static Result<State, CompileException> transform(Node node) {
        return $Result(() -> {
            if (node.is("package")) {
                return new DiscardState();
            }

            var withBody = node.apply(JavaString.apply("body"))
                    .flatMap(Attribute::asNode)
                    .flatMap(node11 -> node11.apply(JavaString.apply("value")))
                    .flatMap(Attribute::asString)
                    .map(body -> compileLine(body).mapValue(compiledBody -> node.with(new JavaString("body"), new NodeAttribute(Content.from(compiledBody)))
                            .unwrapOrElse(node)))
                    .unwrapOrElse(Ok.apply(node)).$();

            var value = withBody.apply(JavaString.apply("lines"))
                    .flatMap(Attribute::asListOfNodes)
                    .map(node11 -> node11.iter()
                            .map(child -> child.apply(JavaString.apply("value")))
                            .flatMap(Iterators::fromOption)
                            .map(Attribute::asString)
                            .flatMap(Iterators::fromOption)
                            .collect(ImmutableLists.into())).map(Compiler::compileLines)
                    .unwrapOrElse(Ok.apply(withBody))
                    .$();

            return new ContinueState(value);
        });
    }

    private static Result<Node, CompileException> compileLines(List<JavaString> lines) {
        return lines.iter()
                .map(Compiler::compileLine)
                .collect(com.meti.api.collect.Collectors.exceptionally(ImmutableLists.into()))
                .mapValue(BlockNode::new);
    }

    public Result<JavaString, CompileException> compile() {
        var lines = new Splitter(input()).split();
        return lines.iter()
                .map(JavaString::strip)
                .map(Compiler::compileLine)
                .collect(Collectors.exceptionally(Collectors.joining()))
                .mapValue(value -> value.unwrapOrElse(JavaString.Empty));
    }

    interface State {

        Option<Node> value();
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