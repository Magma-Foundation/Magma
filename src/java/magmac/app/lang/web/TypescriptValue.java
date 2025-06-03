package magmac.app.lang.web;

import magmac.app.compile.node.Node;
import magmac.app.lang.Serializable;

public interface TypescriptValue extends Serializable {
    Node serialize();
}
