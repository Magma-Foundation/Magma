package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;

import java.util.function.Supplier;

public interface PassResult {
    CompileResult<Tuple2<ParseState, Node>> orElseGet(Supplier<Tuple2<ParseState, Node>> other);
}
