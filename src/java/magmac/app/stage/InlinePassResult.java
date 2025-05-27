package magmac.app.stage;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;

import java.util.function.Supplier;

public record InlinePassResult(Option<Tuple2<ParseState, Node>> option) implements PassResult {
    public static PassResult empty() {
        return new InlinePassResult(new None<>());
    }

    @Override
    public Tuple2<ParseState, Node> orElseGet(Supplier<Tuple2<ParseState, Node>> other) {
        return this.option.orElseGet(other);
    }
}
