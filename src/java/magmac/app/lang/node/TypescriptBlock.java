package magmac.app.lang.node;

import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public final class TypescriptBlock extends Block<TypescriptBlockHeader, TypescriptFunctionSegment> implements TypescriptFunctionSegment {
    public TypescriptBlock(TypescriptBlockHeader header, List<TypescriptFunctionSegment> segments) {
        super(header, segments);
    }

    @Override
    public Node serialize() {
        return new MapNode("block")
                .withNodeSerialized("header", this.header)
                .withNodeListSerialized("children", this.children);
    }
}
