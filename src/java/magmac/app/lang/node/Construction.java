package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Destructors;
import magmac.app.lang.JavaLang;

record Construction(JavaType type) implements JavaLang.Caller {
    public static Option<CompileResult<JavaLang.Caller>> deserialize(Node node) {
        return Destructors.destructWithType("construction", node).map(deserializer -> deserializer.withNode("type", JavaTypes::deserialize).complete(Construction::new));
    }

    public static Rule createConstructionRule() {
        return new TypeRule("construction", new StripRule(new PrefixRule("new ", new NodeRule("type", JavaTypes.createTypeRule()))));
    }

    @Override
    public Node serialize() {
        return new MapNode(this.getClass().getName());
    }
}
