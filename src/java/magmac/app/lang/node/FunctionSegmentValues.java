package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Deserializers;
import magmac.app.lang.JavaDeserializers;
import magmac.app.lang.JavaRules;
import magmac.app.lang.java.JavaAssignmentNode;
import magmac.app.lang.java.JavaFunctionSegmentValue;
import magmac.app.lang.java.JavaPost;

public final class FunctionSegmentValues {
    public static CompileResult<JavaFunctionSegmentValue> deserialize(Node node) {
        return Deserializers.orError("function-segment-value", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeReturn),
                Deserializers.wrap(JavaAssignmentNode::deserialize),
                Deserializers.wrap(JavaDeserializers::deserializeInvocation),
                Deserializers.wrap(node1 -> JavaDeserializers.deserializePost(PostVariant.Increment, node1)),
                Deserializers.wrap(node1 -> JavaDeserializers.deserializePost(PostVariant.Decrement, node1)),
                Deserializers.wrap(JavaDeserializers::deserializeBreak),
                Deserializers.wrap(JavaDeserializers::deserializeContinue),
                Deserializers.wrap(JavaDeserializers::deserializeYield)
        ));
    }

    public static Rule createFunctionSegmentValueRule(Rule value, Rule definition) {
        return new OrRule(Lists.of(
                JavaRules.createInvokableRule(value),
                JavaAssignmentNode.createAssignmentRule(definition, value),
                JavaRules.createReturnRule(value),
                JavaRules.createYieldRule(value),
                JavaPost.createPostRule("post-increment", "++", value),
                JavaPost.createPostRule("post-decrement", "--", value),
                new TypeRule("break", new ExactRule("break")),
                new TypeRule("continue", new ExactRule("continue"))
        ));
    }
}
