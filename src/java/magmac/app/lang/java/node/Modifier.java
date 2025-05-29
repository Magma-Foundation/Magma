package magmac.app.lang.java.node;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.lang.java.Deserializers;

public record Modifier(String value) {
    public static CompileResult<Modifier> deserialize(Node node) {
        return Deserializers.deserialize(node)
                .withString("value")
                .complete(Modifier::new);
    }

    public static Rule createModifiersRule() {
        return new StripRule(new NodeListRule("modifiers", new DelimitedFolder(' '), new StringRule("value")));
    }
}
