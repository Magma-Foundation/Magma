package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public record Symbol(String value) implements JavaType, Value, JavaBase, TypeScriptType {
    @Override
    public Node serialize() {
        return new MapNode("symbol").withString("value", this.value);
    }
}
