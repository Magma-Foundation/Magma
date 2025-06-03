package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.lang.Deserializers;
import magmac.app.lang.java.Lang;

public final class Arguments {
    public static CompileResult<Lang.JavaArgument> deserialize(Node node) {
        return Deserializers.orError("argument", node, Lists.of(
                Deserializers.wrap(Whitespace::deserialize),
                Deserializers.wrap(Values::deserialize)
        ));
    }

    public static Rule createArgumentsRule(Rule value) {
        return NodeListRule.Values("arguments", new OrRule(Lists.of(
                Whitespace.createWhitespaceRule(),
                value
        )));
    }
}
