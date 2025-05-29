package magmac.app.lang.java.function;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.java.Deserializer;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.assign.Assignment;
import magmac.app.lang.java.function.segment.Return;
import magmac.app.lang.java.invoke.Invokable;
import magmac.app.lang.java.value.Post;
import magmac.app.lang.java.value.PostVariant;

public final class FunctionSegmentValues {
    public static CompileResult<FunctionSegmentValue> deserialize(Node node) {
        return Deserializers.orError("function-segment-value", node, Lists.of(
                Deserializers.wrap(Return::deserialize),
                Deserializers.wrap(Assignment::deserialize),
                Deserializers.wrap(Invokable::deserialize),
                Deserializers.wrap(node1 -> Post.deserialize(PostVariant.Increment, node1)),
                Deserializers.wrap(node1 -> Post.deserialize(PostVariant.Decrement, node1)),
                Deserializers.wrap(Break::deserialize),
                Deserializers.wrap(Continue::deserialize)
        ));
    }

    public static Rule createFunctionSegmentValueRule(Rule value, Rule definition) {
        return new OrRule(Lists.of(
                Invokable.createInvokableRule(value),
                Assignment.createAssignmentRule(definition, value),
                Return.createReturnRule(value),
                Post.createPostRule("post-increment", "++", value),
                Post.createPostRule("post-decrement", "--", value),
                new TypeRule("break", new ExactRule("break")),
                new TypeRule("continue", new ExactRule("continue"))
        ));
    }
}
