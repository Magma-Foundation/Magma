package magmac.app.lang.java.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.java.Deserializers;

public record CharNode(String value) implements Value {
    public static Option<CompileResult<CharNode>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "char").map(deserializer -> {
            return deserializer.withString("value").complete(CharNode::new);
        });
    }

    public static Rule createCharRule() {
        return new TypeRule("char", new StripRule(new PrefixRule("'", new SuffixRule(new StringRule("value"), "'"))));
    }
}
