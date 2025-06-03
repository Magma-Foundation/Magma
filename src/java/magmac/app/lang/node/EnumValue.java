package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.lang.Destructors;

record EnumValue(String name, Option<List<JavaValue>> argumentsList) {
    public static CompileResult<EnumValue> deserialize(Node node) {
        return Destructors.destruct(node)
                .withString("name")
                .withNodeListOptionally("arguments", Values::deserializeOrError)
                .complete(tuple -> new EnumValue(tuple.left(), tuple.right()));
    }

    static Rule createEnumValueRule(Rule value) {
        Rule name = new StripRule(FilterRule.Symbol(new StringRule("name")));
        Rule rule = new SuffixRule(LocatingRule.First(name, "(", Arguments.createArgumentsRule(value)), ")");
        return new StripRule(new OrRule(Lists.of(
                Symbols.createSymbolRule("name"),
                rule
        )));
    }
}
