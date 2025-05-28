package magmac.app.stage;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.result.Ok;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;

import java.util.function.Supplier;

public record InlinePassResult(Option<CompileResult<ParseUnit>> option) implements ParseResult {
    public static ParseResult empty() {
        return new InlinePassResult(new None<>());
    }

    public static ParseResult from(ParseState state, Node node) {
        return new InlinePassResult(new Some<>(CompileResults.fromOk(new ParseUnit(state, node))));
    }

    @Override
    public CompileResult<ParseUnit> orElseGet(Supplier<ParseUnit> other) {
        return this.option.orElseGet(() -> CompileResults.fromResult(new Ok<>(other.get())));
    }
}
