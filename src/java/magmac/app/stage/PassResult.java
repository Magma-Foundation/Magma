package magmac.app.stage;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface PassResult {
    PassResult filter(Predicate<Tuple2<ParseState, Node>> predicate);

    <R> Option<R> flatMap(Function<Tuple2<ParseState, Node>, Option<R>> mapper);

    void ifPresent(Consumer<Tuple2<ParseState, Node>> consumer);

    boolean isEmpty();

    boolean isPresent();

    <R> Option<R> map(Function<Tuple2<ParseState, Node>, R> mapper);

    PassResult or(Supplier<PassResult> other);

    Tuple2<ParseState, Node> orElse(Tuple2<ParseState, Node> other);

    Tuple2<ParseState, Node> orElseGet(Supplier<Tuple2<ParseState, Node>> other);

    Option<Tuple2<ParseState, Node>> toOption();
}
