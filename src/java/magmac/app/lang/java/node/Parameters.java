package magmac.app.lang.java.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.lang.ValueFolder;
import magmac.app.lang.java.Deserializers;

public class Parameters {
    public static CompileResult<Parameter> deserialize(Node node) {
        return Deserializers.orError("parameter", node, Lists.of(
                Deserializers.wrap(Whitespace::deserialize),
                Deserializers.wrap(Definition::deserialize)
        ));
    }

    public static NodeListRule createParametersRule(Rule definition) {
        return new NodeListRule("parameters", new ValueFolder(), new OrRule(Lists.of(
                Whitespace.createWhitespaceRule(),
                definition
        )));
    }
}
