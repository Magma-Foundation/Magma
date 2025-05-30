package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.TypeRule;

public record PlantUMLDependency(String child, String parent) implements PlantUMLRootSegment {
    static TypeRule createDependencyRule() {
        return new TypeRule("dependency", LocatingRule.First(new StringRule("child"), " --> ", new StringRule("parent")));
    }

    @Override
    public Node serialize() {
        return new MapNode("dependency").withString("parent", this.parent).withString("child", this.child);
    }
}