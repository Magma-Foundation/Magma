package magmac.app.lang.node;

/**
 * Represents a keyword modifier such as {@code public} or {@code static}.
 */

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.lang.Destructors;
import magmac.app.lang.Serializable;

public record Modifier(String value) implements Serializable {
    public static CompileResult<Modifier> deserialize(Node node) {
        return Destructors.destruct(node)
                .withString("value")
                .complete(Modifier::new);
    }

    public static Rule createModifiersRule() {
        return new StripRule(NodeListRule.createNodeListRule("modifiers", new DelimitedFolder(' '), new StringRule("value")));
    }

    @Override
    public Node serialize() {
        return new MapNode("modifier").withString("value", this.value);
    }
}
