package magmac.app.lang.node;

/**
 * Node representing a structure declaration in PlantUML such as
 * {@code class MyClass}.
 */

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.TypeRule;

public record PlantUMLStructure(PlantUMLStructureType type, String name) implements PlantUMLRootSegment {
    public static Rule createStructureRule(String type) {
        return new TypeRule(type, new PrefixRule(type + " ", new StringRule("name")));
    }

    @Override
    public Node serialize() {
        return new MapNode(this.type.name().toLowerCase()).withString("name", this.name);
    }
}
