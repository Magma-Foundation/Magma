package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Deserializers;
import magmac.app.lang.JavaDeserializers;
import magmac.app.lang.JavaRules;
import magmac.app.lang.LazyRule;

public class FunctionSegments {
    public static CompileResult<JavaFunctionSegment> deserialize(Node node) {
        return Deserializers.orError("function-segment", node, Lists.of(
                Deserializers.wrap(Whitespace::deserialize),
                Deserializers.wrap(JavaDeserializers::deserializeFunctionStatement),
                Deserializers.wrap(JavaDeserializers::deserializeBlock),
                Deserializers.wrap(JavaDeserializers::deserializeReturn),
                Deserializers.wrap(CaseNode::deserialize)
        ));
    }

    public static Rule initFunctionSegmentRule(LazyRule functionSegmentRule, Rule value, Rule definition) {
        Rule functionSegmentValueRule = FunctionSegmentValues.createFunctionSegmentValueRule(value, definition);

        Rule rule = new OrRule(Lists.of(
                new TypeRule("whitespace", new StripRule(new ExactRule(";"))),
                Whitespace.createWhitespaceRule(),
                JavaRules.createStatementRule(functionSegmentValueRule),
                JavaRules.createBlockRule(functionSegmentRule, value, definition),
                JavaReturnNode.createReturnRule(value),
                CaseNode.createCaseRule(value, functionSegmentRule)
        ));

        return functionSegmentRule.set(new StripRule("before", rule, ""));
    }

}
