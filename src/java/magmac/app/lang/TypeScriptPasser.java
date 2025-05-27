package magmac.app.lang;

import magmac.api.None;
import magmac.api.Tuple2;
import magmac.app.compile.node.Node;
import magmac.app.stage.Passer;
import magmac.app.stage.parse.ParseState;

import magmac.api.Option;

public class TypeScriptPasser implements Passer {
    @Override
    public Option<Tuple2<ParseState, Node>> pass(ParseState state, Node node) {
        return new None<>();
    }
}
