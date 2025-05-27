package magmac.app.lang;

import magmac.api.Tuple2;
import magmac.app.compile.node.Node;
import magmac.app.stage.Passer;
import magmac.app.stage.parse.ParseState;

import java.util.Optional;

public class TypeScriptPasser implements Passer {
    @Override
    public Optional<Tuple2<ParseState, Node>> pass(ParseState state, Node node) {
        return Optional.empty();
    }
}
