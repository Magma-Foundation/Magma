package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.CommonLang;
import magmac.app.lang.LazyRule;
import magmac.app.lang.ValueFolder;
import magmac.app.lang.Deserializers;

record Lambda() implements Value {
    public static Option<CompileResult<Lambda>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "lambda").map(deserializer -> deserializer.complete(Lambda::new));
    }

    public static Rule createLambdaRule(LazyRule value, Rule functionSegment, String infix, Rule definition) {
        Rule afterInfix = new OrRule(Lists.of(
                new StripRule(new PrefixRule("{", new SuffixRule(CommonLang.Statements("children", functionSegment), "}"))),
                new NodeRule("value", value)
        ));

        Rule parameters = Lambda.createLambdaParameterRule(definition);
        Rule withParentheses = new PrefixRule("(", new SuffixRule(parameters, ")"));
        Rule withoutParentheses = Symbols.createSymbolRule("param");

        Rule beforeInfix = new OrRule(Lists.of(
                withParentheses,
                withoutParentheses
        ));

        return new TypeRule("lambda", LocatingRule.First(new StripRule(beforeInfix), infix, afterInfix));
    }

    private static Rule createLambdaParameterRule(Rule definition) {
        return NodeListRule.createNodeListRule("parameters", new ValueFolder(), new OrRule(Lists.of(
                definition,
                Symbols.createSymbolRule("param")
        )));
    }
}
