package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Deserializers;

final class FunctionSegmentValues {
    public static CompileResult<FunctionSegmentValue> deserialize(Node node) {
        return Deserializers.orError("function-segment-value", node, Lists.of(
                Deserializers.wrap(ReturnNode::deserialize),
                Deserializers.wrap(AssignmentNode::deserialize),
                Deserializers.wrap(InvokableNode::deserialize),
                Deserializers.wrap(node1 -> Post.deserialize(PostVariant.Increment, node1)),
                Deserializers.wrap(node1 -> Post.deserialize(PostVariant.Decrement, node1)),
                Deserializers.wrap(Break::deserialize),
                Deserializers.wrap(Continue::deserialize)
        ));
    }

    public static Rule createFunctionSegmentValueRule(Rule value, Rule definition) {
        return new OrRule(Lists.of(
                InvokableNode.createInvokableRule(value),
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
