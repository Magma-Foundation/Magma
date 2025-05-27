package magmac.app.lang;

import magmac.app.compile.node.Node;
import magmac.app.stage.InlinePassResult;
import magmac.app.stage.PassResult;
import magmac.app.stage.Passer;
import magmac.app.stage.parse.ParseState;

public class TypeScriptPasser implements Passer {
    @Override
    public PassResult pass(ParseState state, Node node) {
        return InlinePassResult.empty();
    }
}
