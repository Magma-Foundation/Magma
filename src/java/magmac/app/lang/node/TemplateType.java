package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Deserializers;

public record TemplateType(Base base, List<Type> right) implements Type {
    public static Option<CompileResult<TemplateType>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "template").map(deserializer -> deserializer
                .withNode("base", TemplateType::deserializeBase)
                .withNodeList("arguments", Types::deserialize)
                .complete(tuple -> new TemplateType(tuple.left(), tuple.right())));
    }

    private static CompileResult<Base> deserializeBase(Node node) {
        return Deserializers.orError("base", node, Lists.of(
                Deserializers.wrap(Symbols::deserialize),
                Deserializers.wrap(QualifiedType::deserializeQualified)
        ));
    }

    public static Rule createTemplateRule(Rule type) {
        Rule base = new NodeRule("base", new OrRule(Lists.of(
                Symbols.createSymbolRule(),
                QualifiedType.createQualifiedRule()
        )));

        Rule arguments = NodeListRule.Values("arguments", type);
        return new TypeRule("template", new StripRule(new SuffixRule(LocatingRule.First(base, "<", arguments), ">")));
    }
}
