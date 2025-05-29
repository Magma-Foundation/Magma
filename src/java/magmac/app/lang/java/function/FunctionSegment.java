package magmac.app.lang.java.function;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.lang.CommonLang;
import magmac.app.lang.LazyRule;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.Whitespace;

public interface FunctionSegment {
    static CompileResult<FunctionSegment> deserialize(Node node) {
        return Deserializers.orError("function-segment", node, Lists.of(
                Deserializers.wrap(Whitespace::deserialize),
                Deserializers.wrap(FunctionStatement::deserialize),
                Deserializers.wrap(Block::deserialize)
        ));
    }

    static Rule initFunctionSegmentRule(LazyRule functionSegmentRule, Rule value, Rule definition) {
        Rule functionSegmentValueRule = FunctionSegmentValues.createFunctionSegmentValueRule(value, definition);

        Rule rule = new OrRule(Lists.of(
                Whitespace.createWhitespaceRule(),
                FunctionStatement.createStatementRule(functionSegmentValueRule),
                CommonLang.createBlockRule(functionSegmentRule, value, definition)
        ));

        return functionSegmentRule.set(new StripRule("before", rule, ""));
    }
}
