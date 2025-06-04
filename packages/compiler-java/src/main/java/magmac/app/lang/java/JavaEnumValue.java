package magmac.app.lang.java;

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
import magmac.app.lang.CommonRules;
import magmac.app.lang.Destructors;
import magmac.app.lang.JavaRules;

record JavaEnumValue(String name, Option<List<JavaLang.Value>> argumentsList) {
    public static CompileResult<JavaEnumValue> deserialize(Node node) {
        return Destructors.destruct(node)
                .withString("name")
                .withNodeListOptionally("arguments", JavaDeserializers::deserializeValueOrError)
                .complete(tuple -> new JavaEnumValue(tuple.left(), tuple.right()));
    }

    static Rule createEnumValueRule(Rule value) {
        Rule name = new StripRule(FilterRule.Symbol(new StringRule("name")));
        Rule rule = new SuffixRule(LocatingRule.First(name, "(", JavaRules.createArgumentsRule(value)), ")");
        return new StripRule(new OrRule(Lists.of(
                CommonRules.createSymbolRule("name"),
                rule
        )));
    }
}
