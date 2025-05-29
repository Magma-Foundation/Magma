package magmac.app.lang.java.function;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.CommonLang;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.value.Invokable;
import magmac.app.lang.java.function.segment.Return;
import magmac.app.lang.java.Whitespace;
import magmac.app.lang.java.assign.Assignment;

public interface FunctionSegment {
    static CompileResult<FunctionSegment> deserialize(Node node) {
        return Deserializers.orError("function-segment", node, Lists.of(
                Deserializers.wrap(Whitespace::deserialize),
                FunctionStatement::deserialize
        ));
    }

    static Rule createFunctionSegmentValueRule(Rule value, Rule definition) {
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
