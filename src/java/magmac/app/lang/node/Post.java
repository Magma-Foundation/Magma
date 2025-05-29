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

record Post(PostVariant variant, Value value) implements FunctionSegmentValue {
    public static Rule createPostRule(String type, String suffix, Rule value) {
        return new TypeRule(type, new StripRule(new SuffixRule(new NodeRule("child", value), suffix)));
    }

    public static Option<CompileResult<FunctionSegmentValue>> deserialize(PostVariant variant, Node node) {
        return Deserializers.deserializeWithType(node, variant.type()).map(deserializer -> deserializer
                .withNode("child", Values::deserializeOrError)
                .complete(child -> new Post(variant, child)));
    }
}
