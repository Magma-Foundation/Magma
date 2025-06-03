package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.lang.java.JavaDeserializers;
import magmac.app.lang.JavaRules;
import magmac.app.lang.ValueFolder;
import magmac.app.lang.Deserializers;
import magmac.app.lang.java.JavaParameter;

public final class Parameters {
    public static CompileResult<JavaParameter> deserialize(Node node) {
        return Deserializers.orError("parameter", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeWhitespace),
                Deserializers.wrap(JavaDeserializers::deserializeTypedDefinition)
        ));
    }

    public static Rule createParametersRule(Rule definition) {
        return NodeListRule.createNodeListRule("parameters", new ValueFolder(), new OrRule(Lists.of(
                JavaRules.createWhitespaceRule(),
                definition
        )));
    }
}
