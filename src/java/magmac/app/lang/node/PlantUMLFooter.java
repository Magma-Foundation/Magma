package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.TypeRule;

public class PlantUMLFooter implements PlantUMLRootSegment {
    static TypeRule createRule() {
        return new TypeRule("footer", new ExactRule("@enduml"));
    }

    @Override
    public Node serialize() {
        return new MapNode("footer");
    }
}
