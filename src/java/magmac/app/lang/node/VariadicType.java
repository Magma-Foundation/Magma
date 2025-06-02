package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Destructors;

public record VariadicType(JavaType child) implements JavaType {
    public static Option<CompileResult<JavaType>> deserialize(Node node) {
        return Destructors.destructWithType("variadic", node).map(deserializer -> deserializer.withNode("child", JavaTypes::deserialize)
                .complete(VariadicType::new));
    }

    public static Rule createVariadicRule(Rule rule) {
        NodeRule child = new NodeRule("child", rule);
        return new TypeRule("variadic", new StripRule(new SuffixRule(child, "...")));
    }
}
