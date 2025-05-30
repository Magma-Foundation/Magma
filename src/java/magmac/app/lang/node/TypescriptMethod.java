package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Serializable;

public record TypescriptMethod(MethodHeader header) implements TypescriptStructureMember {
    @Override
    public Node serialize() {
        return new MapNode("method")
                .withNodeAndSerializer("header", this.header, Serializable::serialize);
    }
}
