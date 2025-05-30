package magmac.app.lang.node;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.lang.Deserializers;

public record Modifier(String value) {
    public static CompileResult<Modifier> deserialize(Node node) {
        return Deserializers.destruct(node)
                .withString("value")
                .complete(Modifier::new);
    }

    public static Rule createModifiersRule() {
        return new StripRule(NodeListRule.createNodeListRule("modifiers", new DelimitedFolder(' '), new StringRule("value")));
    }
}
