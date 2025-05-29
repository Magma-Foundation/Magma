package magmac.app.lang.java.type;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.java.Type;
import magmac.app.lang.java.value.Symbols;

public record TemplateType(String base, List<Type> right) implements Type {
    public static Option<CompileResult<TemplateType>> deserialize(Node node) {
        return node.deserializeWithType("template").map(deserializer -> deserializer
                .withString("base")
                .withNodeList("arguments", Types::deserialize)
                .complete(tuple -> new TemplateType(tuple.left(), tuple.right())));
    }

    public static Rule createTemplateRule(Rule type) {
        Rule base = new OrRule(Lists.of(
                Symbols.createSymbolRule("base"),
                Symbols.createSegmentsRule("segments")
        ));

        Rule arguments = NodeListRule.Values("arguments", type);
        return new TypeRule("template", new StripRule(new SuffixRule(LocatingRule.First(base, "<", arguments), ">")));
    }
}
