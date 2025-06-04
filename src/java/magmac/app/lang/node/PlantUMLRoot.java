package magmac.app.lang.node;

/**
 * Top level element for a PlantUML file. It contains all diagram segments.
 */

import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.fold.StatementFolder;
import magmac.app.lang.Serializable;

public record PlantUMLRoot(List<PlantUMLRootSegment> segments) implements Serializable {
    public static Rule createRule() {
        return NodeListRule.createNodeListRule("segments", new StatementFolder(), PlantUMLRootSegments.createRootSegmentRule());
    }

    @Override
    public Node serialize() {
        return new MapNode("root").withNodeListAndSerializer("segments", this.segments, PlantUMLRootSegment::serialize);
    }
}
