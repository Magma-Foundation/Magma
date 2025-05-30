package magmac.app.lang.node;

import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.CommonLang;
import magmac.app.lang.Serializable;

public record Root<T extends Serializable>(List<T> children) implements Serializable {
    public static Rule createRule() {
        return new TypeRule("root", CommonLang.Statements("children", JavaRootSegments.getChildRule()));
    }

    @Override
    public Node serialize() {
        return new MapNode().withSerializedNodeList("children", this.children, Serializable::serialize);
    }
}
