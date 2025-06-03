package magmac.app.lang.node;

import magmac.app.compile.node.Node;
import magmac.app.lang.Serializable;

public interface TypeScriptValue extends Serializable {
    Node serialize();
}
