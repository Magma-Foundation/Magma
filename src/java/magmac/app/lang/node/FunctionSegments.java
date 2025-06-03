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
import magmac.app.lang.java.JavaDeserializers;
import magmac.app.lang.JavaRules;
import magmac.app.lang.LazyRule;
import magmac.app.lang.java.JavaCaseNode;
import magmac.app.lang.java.JavaFunctionSegment;

public class FunctionSegments {
    public static CompileResult<JavaFunctionSegment> deserialize(Node node) {
        return Deserializers.orError("function-segment", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeWhitespace),
                Deserializers.wrap(JavaDeserializers::deserializeFunctionStatement),
                Deserializers.wrap(JavaDeserializers::deserializeBlock),
                Deserializers.wrap(JavaDeserializers::deserializeReturn),
                Deserializers.wrap(JavaCaseNode::deserialize)
        ));
    }

    public static Rule initFunctionSegmentRule(LazyRule functionSegmentRule, Rule value, Rule definition) {
        Rule functionSegmentValueRule = FunctionSegmentValues.createFunctionSegmentValueRule(value, definition);

        Rule rule = new OrRule(Lists.of(
                new TypeRule("whitespace", new StripRule(new ExactRule(";"))),
                JavaRules.createWhitespaceRule(),
                JavaRules.createStatementRule(functionSegmentValueRule),
                JavaRules.createBlockRule(functionSegmentRule, value, definition),
                JavaRules.createReturnRule(value),
                JavaCaseNode.createCaseRule(value, functionSegmentRule)
        ));

        return functionSegmentRule.set(new StripRule("before", rule, ""));
    }

}
