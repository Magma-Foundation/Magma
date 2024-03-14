package com.meti.compile.procedure;

import com.meti.collect.Index;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Options.$$;
import static com.meti.collect.option.Options.$Option;
import static com.meti.collect.option.Some.Some;

public class InvocationLexer implements Lexer {
    private final JavaString stripped;

    public InvocationLexer(JavaString javaString) {
        this.stripped = javaString;
    }

    protected String prefix() {
        return "";
    }

    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var prefix = stripped.firstIndexOfSlice(prefix()).$();
            if (!prefix.isStart()) $$();

            var end = stripped.lastIndexOfChar(')').$();
            var furthestComputer = stripped.sliceTo(end).streamReverse().extend(stripped::apply).foldRight(new State(0, None()), (state, tuple) -> {
                if (tuple.b() == '(' && state.isLevel()) return state.evaluate(tuple.a());
                if (tuple.b() == ')') return state.increase();
                if (tuple.b() == '(') return state.decrease();
                return state;
            });

            var prefixIndex = prefix.next("new ".length()).$();
            var start = furthestComputer.furthest.$();
            var caller = new Content(stripped.sliceBetween(prefixIndex.to(start).$()), 0);
            var list = stripped.sliceBetween(start.next().$().to(end).$()).split(",").map(arg -> new Content(arg, 0)).collect(Collectors.toList());

            return new InvocationNode(caller, list);
        });
    }

    private record State(int depth, Option<Index> furthest) {
        public State increase() {
            return new State(depth + 1, furthest);
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State evaluate(Index c) {
            if (furthest.isEmpty()) return new State(depth, Some(c));
            return new State(depth, furthest.map(value -> value.max(c)));
        }

        public State decrease() {
            return new State(depth - 1, furthest);
        }
    }
}