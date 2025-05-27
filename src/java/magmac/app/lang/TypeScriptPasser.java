package magmac.app.lang;

import magmac.api.None;
import magmac.api.Tuple2;
import magmac.app.compile.node.Node;
import magmac.app.stage.InlinePassResult;
import magmac.app.stage.PassResult;
import magmac.app.stage.Passer;
import magmac.app.stage.parse.ParseState;

import magmac.api.Option;

public class TypeScriptPasser implements Passer {
    @Override
    public PassResult pass(ParseState state, Node node) {
        return new InlinePassResult(new None<>());
    }
}
