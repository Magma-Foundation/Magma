package magmac.app.lang.java.type;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.java.Type;
import magmac.app.lang.java.value.Symbol;

public record TemplateType(String base) implements Type {
    public static Option<CompileResult<TemplateType>> deserialize(Node node) {
        return node.deserializeWithType("template").map(deserializer -> deserializer
                .withString("base")
                .complete(TemplateType::new));
    }

    public static Rule createTemplateRule(Rule type) {
        Rule base = Symbol.createSymbolRule("base");
        Rule arguments = NodeListRule.Values("arguments", type);
        return new TypeRule("template", new StripRule(new SuffixRule(LocatingRule.First(base, "<", arguments), ">")));
    }
}
