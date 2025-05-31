package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.StatementFolder;
import magmac.app.lang.Deserializers;
import magmac.app.lang.LazyRule;
import magmac.app.lang.Serializable;

public interface FunctionSegment extends Serializable {
    static CompileResult<FunctionSegment> deserialize(Node node) {
        return Deserializers.orError("function-segment", node, Lists.of(
                Deserializers.wrap(Whitespace::deserialize),
                Deserializers.wrap(FunctionStatement::deserialize),
                Deserializers.wrap(Block::deserialize),
                Deserializers.wrap(ReturnNode::deserialize)
        ));
    }

    static Rule initFunctionSegmentRule(LazyRule functionSegmentRule, Rule value, Rule definition) {
        Rule functionSegmentValueRule = FunctionSegmentValues.createFunctionSegmentValueRule(value, definition);

        Rule rule = new OrRule(Lists.of(
                new TypeRule("whitespace", new StripRule(new ExactRule(";"))),
                Whitespace.createWhitespaceRule(),
                FunctionStatement.createStatementRule(functionSegmentValueRule),
                Block.createBlockRule(functionSegmentRule, value, definition),
                ReturnNode.createReturnRule(value),
                FunctionSegment.createCaseRule(value, functionSegmentRule)
        ));

        return functionSegmentRule.set(new StripRule("before", rule, ""));
    }

    private static Rule createCaseRule(Rule value, Rule segment) {
        Rule typeRule = Types.createTypeRule();
        Rule name = new StripRule(new StringRule("name"));
        Rule last = LocatingRule.Last(new NodeRule("type", typeRule), " ", name);
        Rule definitions = new TypeRule("case-definition", new OrRule(Lists.of(
                new StripRule(last),
                name
        )));

        Rule beforeArrow = NodeListRule.Values("definitions", definitions);
        StripRule rightRule = new StripRule(new SuffixRule(new NodeRule("value", value), ";"));
        Rule childRule = LocatingRule.First(beforeArrow, "->", new OrRule(Lists.of(
                rightRule,
                new StripRule(new PrefixRule("{", new SuffixRule(NodeListRule.createNodeListRule("children", new StatementFolder(), segment), "}")))
        )));

        return new TypeRule("case", new StripRule(new PrefixRule("case", childRule)));
    }
}
