package magmac.app.lang.node;

import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Serializable;

public record TypeScriptRoots(List<TypeScriptRootSegment> children) implements Serializable {
    @Override
    public Node serialize() {
        return new MapNode().withNodeListAndSerializer("children", this.children, Serializable::serialize);
    }
}
