package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.TypeRule;

public class PlantUMLHeader implements PlantUMLRootSegment {
    static TypeRule createRule() {
        return new TypeRule("header", new ExactRule("@startuml\nskinparam linetype ortho"));
    }

    @Override
    public Node serialize() {
        return new MapNode("header");
    }
}
