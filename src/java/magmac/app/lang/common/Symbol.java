package magmac.app.lang.common;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Serializable;

public class Symbol implements Serializable {
    protected final String value;

    public Symbol(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public Node serialize() {
        return new MapNode("symbol").withString("value", this.value);
    }
}
