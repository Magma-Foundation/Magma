package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.web.TypescriptValue;

public record Symbol(String value) implements JavaType, JavaValue, JavaBase, TypeScriptType, LambdaParameter, LambdaHeader, TypescriptValue {
    @Override
    public Node serialize() {
        return new MapNode("symbol").withString("value", this.value);
    }
}
