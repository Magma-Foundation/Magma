package magmac.app.lang.node;

import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public record TypeScriptImport(List<Segment> values, List<Segment> segments) implements TypeScriptRootSegment {
    @Override
    public Node serialize() {
        return new MapNode("import")
                .withSerializedNodeList("children", this.values, Segment::serialize)
                .withSerializedNodeList("segments", this.segments, Segment::serialize);
    }
}
