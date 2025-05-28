package magmac.app.stage;

import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;

public record ParseUnit(ParseState state, Node node) {
    public ParseState left() {
        return this.state;
    }

    public Node right() {
        return this.node;
    }
}
