package magmac.app.stage;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public record InlinePassResult(Option<Tuple2<ParseState, Node>> option) implements PassResult {
    public static PassResult empty() {
        return new InlinePassResult(new None<>());
    }

    @Override
    public PassResult or(Supplier<PassResult> other) {
        return new InlinePassResult(this.option.or(() -> other.get().toOption()));
    }

    @Override
    public PassResult filter(Predicate<Tuple2<ParseState, Node>> predicate) {
        return new InlinePassResult(this.option.filter(predicate));
    }

    @Override
    public <R> Option<R> flatMap(Function<Tuple2<ParseState, Node>, Option<R>> mapper) {
        return this.option.flatMap(mapper);
    }

    @Override
    public void ifPresent(Consumer<Tuple2<ParseState, Node>> consumer) {
        this.option.ifPresent(consumer);
    }

    @Override
    public boolean isEmpty() {
        return this.option.isEmpty();
    }

    @Override
    public boolean isPresent() {
        return this.option.isPresent();
    }

    @Override
    public <R> Option<R> map(Function<Tuple2<ParseState, Node>, R> mapper) {
        return this.option.map(mapper);
    }

    @Override
    public Tuple2<ParseState, Node> orElse(Tuple2<ParseState, Node> other) {
        return this.option.orElse(other);
    }

    @Override
    public Tuple2<ParseState, Node> orElseGet(Supplier<Tuple2<ParseState, Node>> other) {
        return this.option.orElseGet(other);
    }

    @Override
    public Option<Tuple2<ParseState, Node>> toOption() {
        return this.option;
    }
}
