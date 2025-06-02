package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.JavaNodes;

public interface Value extends JavaNodes.Caller, JavaNodes.Argument, Assignable {
    @Override
    default Node serialize() {
        return new MapNode(this.getClass().getName());
    }
}
