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
                .unwrapOrThrow(new CompileException("Cannot render: " + node))
                .flatMapValue(value -> value);
    }

    private static Result<State, CompileException> transform(Node node) {
        return $Result(() -> {
            if (node.is("package")) {
                return new DiscardState();
            }

            return new ContinueState(node);
        });
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