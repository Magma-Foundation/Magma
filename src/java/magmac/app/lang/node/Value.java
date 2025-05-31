package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public interface Value extends Caller, Argument, Assignable {
    @Override
    default Node serialize() {
        return new MapNode(this.getClass().getName());
    }
}
