package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.JavaLang;

public interface Value extends JavaLang.Caller, JavaLang.Argument, Assignable {
    @Override
    default Node serialize() {
        return new MapNode(this.getClass().getName());
    }
}
