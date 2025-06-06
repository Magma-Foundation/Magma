package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Destructors;
import magmac.app.lang.ValueFolder;

public record JavaEnumValues(List<JavaEnumValue> values) implements JavaStructureMember {
    public static TypeRule createEnumValuesRule(Rule value) {
        var enumValue = JavaEnumValue.createEnumValueRule(value);
        var enumValues = NodeListRule.createNodeListRule("children", new ValueFolder(), enumValue);
        Rule withEnd = new StripRule(new SuffixRule(enumValues, ";"));
        return new TypeRule("enum-values", new OrRule(Lists.of(
                withEnd,
                enumValues
        )));
    }

    public static Option<CompileResult<JavaStructureMember>> deserialize(Node node) {
        return Destructors.destructWithType("enum-values", node)
                .map(deserializer -> deserializer.withNodeList("children", JavaEnumValue::deserialize)
                        .complete(JavaEnumValues::new));
    }
}
