package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
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
import magmac.app.lang.Destructors;
import magmac.app.lang.LazyRule;
import magmac.app.lang.ValueFolder;

record Lambda(LambdaHeader header, LambdaContent content) implements Value {
    public static Option<CompileResult<Lambda>> deserialize(Node node) {
        return Destructors.destructWithType("lambda", node).map(deserializer -> deserializer
                .withNode("header", LambdaHeaders::deserialize)
                .withNode("content", LambdaContents::deserialize)
                .complete(tuple -> new Lambda(tuple.left(), tuple.right())));
    }

    public static Rule createLambdaRule(LazyRule value, Rule functionSegment, String infix, Rule definition) {
        Rule afterInfix = new OrRule(Lists.of(
                new TypeRule("block", new StripRule(new PrefixRule("{", new SuffixRule(CommonLang.Statements("children", functionSegment), "}")))),
                new TypeRule("value", new NodeRule("value", value))
        ));

        Rule parameters = Lambda.createLambdaParameterRule(definition);
        Rule withParentheses = new TypeRule("multiple", new StripRule(new PrefixRule("(", new SuffixRule(parameters, ")"))));
        Rule withoutParentheses = Symbols.createSymbolRule();

        Rule header = new NodeRule("header", new OrRule(Lists.of(
                withParentheses,
                withoutParentheses
        )));

        return new TypeRule("lambda", LocatingRule.First(header, infix, new NodeRule("content", afterInfix)));
    }

    private static Rule createLambdaParameterRule(Rule definition) {
        return NodeListRule.createNodeListRule("parameters", new ValueFolder(), new OrRule(Lists.of(
                definition,
                Symbols.createSymbolRule()
        )));
    }

    @Override
    public Node serialize() {
        return new MapNode("lambda")
                .withNodeSerialized("header", this.header)
                .withNodeSerialized("content", this.content);
    }
}
