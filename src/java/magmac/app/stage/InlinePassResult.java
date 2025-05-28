package magmac.app.stage;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.Tuple2;
import magmac.api.result.Ok;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;

import java.util.function.Supplier;

public record InlinePassResult(Option<CompileResult<Tuple2<ParseState, Node>>> option) implements PassResult {
    public static PassResult empty() {
        return new InlinePassResult(new None<>());
    }

    public static PassResult from(ParseState state, Node node) {
        return new InlinePassResult(new Some<>(CompileResults.fromOk(new Tuple2<>(state, node))));
    }

    @Override
    public CompileResult<Tuple2<ParseState, Node>> orElseGet(Supplier<Tuple2<ParseState, Node>> other) {
        return this.option.orElseGet(() -> CompileResults.fromResult(new Ok<>(other.get())));
    }
}
