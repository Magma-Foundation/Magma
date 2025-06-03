package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Destructors;

record NumberNode(String value) implements JavaValue, TypeScriptValue {
    public static Option<CompileResult<JavaValue>> deserialize(Node node) {
        return Destructors.destructWithType("number", node).map(deserializer -> deserializer.withString("value").complete(NumberNode::new));
    }

    public static Rule createNumberRule() {
        return new TypeRule("number", new StripRule(FilterRule.Number(new StringRule("value"))));
    }

    @Override
    public Node serialize() {
        return new MapNode("number");
    }
}
