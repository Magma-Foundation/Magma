package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.lang.Deserializers;
import magmac.app.lang.java.JavaDeserializers;
import magmac.app.lang.JavaRules;
import magmac.app.lang.java.JavaLang;

public final class Arguments {
    public static CompileResult<JavaLang.JavaArgument> deserialize(Node node) {
        return Deserializers.orError("argument", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeWhitespace),
                Deserializers.wrap(JavaDeserializers::deserializeValue)
        ));
    }

    public static Rule createArgumentsRule(Rule value) {
        return NodeListRule.Values("arguments", new OrRule(Lists.of(
                JavaRules.createWhitespaceRule(),
                value
        )));
    }
}
