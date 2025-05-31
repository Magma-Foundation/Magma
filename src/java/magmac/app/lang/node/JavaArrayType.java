package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Deserializers;

public final class JavaArrayType implements JavaType {
    public final JavaType inner;

    public JavaArrayType(JavaType arrayType) {
        this.inner = arrayType;
    }

    public static Rule createArrayRule(Rule rule) {
        NodeRule child = new NodeRule("child", rule);
        return new TypeRule("array", new StripRule(new SuffixRule(child, "[]")));
    }

    public static Option<CompileResult<JavaType>> deserialize(Node node) {
        return Deserializers.deserializeWithType("array", node)
                .map(deserializer -> deserializer.withNode("child", Types::deserialize)
                        .complete(JavaArrayType::new));
    }
}
