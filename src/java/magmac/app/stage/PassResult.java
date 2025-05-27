package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;

import java.util.function.Supplier;

public interface PassResult {
    Tuple2<ParseState, Node> orElseGet(Supplier<Tuple2<ParseState, Node>> other);
}
