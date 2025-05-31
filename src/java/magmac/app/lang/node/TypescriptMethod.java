package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public record TypescriptMethod(
        ParameterizedMethodHeader<TypeScriptParameter> header) implements TypescriptStructureMember {
    @Override
    public Node serialize() {
        return new MapNode("method").withNodeSerialized("header", this.header);
    }
}
