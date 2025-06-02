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

final class FunctionSegmentValues {
    public static CompileResult<FunctionSegmentValue> deserialize(Node node) {
        return Deserializers.orError("function-segment-value", node, Lists.of(
                Deserializers.wrap(ReturnNode::deserialize),
                Deserializers.wrap(AssignmentNode::deserialize),
                Deserializers.wrap(JavaDeserializers::deserializeInvocation),
                Deserializers.wrap(node1 -> Post.deserialize(PostVariant.Increment, node1)),
                Deserializers.wrap(node1 -> Post.deserialize(PostVariant.Decrement, node1)),
                Deserializers.wrap(Break::deserialize),
                Deserializers.wrap(Continue::deserialize),
                Deserializers.wrap(YieldNode::deserialize)
        ));
    }

    public static Rule createFunctionSegmentValueRule(Rule value, Rule definition) {
        return new OrRule(Lists.of(
                JavaRules.createInvokableRule(value),
                AssignmentNode.createAssignmentRule(definition, value),
                ReturnNode.createReturnRule(value),
                YieldNode.createYieldRule(value),
                Post.createPostRule("post-increment", "++", value),
                Post.createPostRule("post-decrement", "--", value),
                new TypeRule("break", new ExactRule("break")),
                new TypeRule("continue", new ExactRule("continue"))
        ));
    }
}
