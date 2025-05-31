package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Deserializers;
import magmac.app.lang.ValueFolder;

public record EnumValues(List<EnumValue> values) implements JavaStructureMember {
    public static TypeRule createEnumValuesRule(Rule value) {
        Rule name = new StripRule(FilterRule.Symbol(new StringRule("name")));
        Rule rule = new SuffixRule(LocatingRule.First(name, "(", Arguments.createArgumentsRule(value)), ")");
        Rule enumValue = new StripRule(new OrRule(Lists.of(
                Symbols.createSymbolRule("value"),
                rule
        )));

        Rule enumValues = NodeListRule.createNodeListRule("children", new ValueFolder(), enumValue);
        Rule withEnd = new StripRule(new SuffixRule(enumValues, ";"));
        return new TypeRule("enum-values", new OrRule(Lists.of(
                withEnd,
                enumValues
        )));
    }

    public static Option<CompileResult<JavaStructureMember>> deserialize(Node node) {
        return Deserializers.deserializeWithType("enum-values", node).map(deserializer -> deserializer.withNodeList("children", EnumValue::deserialize)
                .complete(EnumValues::new));
    }
}
