package magmac.app.lang.java.function;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.CommonLang;
import magmac.app.lang.java.Deserializer;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.assign.Assignment;
import magmac.app.lang.java.function.segment.Return;
import magmac.app.lang.java.invoke.Invokable;

public final class FunctionSegmentValues {
    public static CompileResult<FunctionSegmentValue> deserialize(Node node) {
        return Deserializers.orError("function-segment-value", node, Lists.<Deserializer<FunctionSegmentValue>>of(
                Return::deserialize,
                Assignment::deserialize,
                Deserializers.wrap(Invokable::deserialize)
        ));
    }

    public static Rule createFunctionSegmentValueRule(Rule value, Rule definition) {
        return new OrRule(Lists.of(
                Invokable.createInvokableRule(value),
                Assignment.createAssignmentRule(definition, value),
                Return.createReturnRule(value),
                CommonLang.createPostRule("post-increment", "++", value),
                CommonLang.createPostRule("post-decrement", "--", value),
                new TypeRule("break", new ExactRule("break")),
                new TypeRule("continue", new ExactRule("continue"))
        ));
    }
}
