package magmac.app.lang.java.assign;

import magmac.api.Option;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.function.FunctionSegmentValue;
import magmac.app.lang.java.value.Value;
import magmac.app.lang.java.value.Values;

public record Assignment(Assignable assignable, Value value) implements FunctionSegmentValue {
    public static Option<CompileResult<FunctionSegmentValue>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "assignment").map(deserializer -> deserializer
                .withNode("destination", Assignables::deserializeError)
                .withNode("source", Values::deserializeOrError)
                .complete(tuple -> new Assignment(tuple.left(), tuple.right())));
    }

    public static Rule createAssignmentRule(Rule definition, Rule value) {
        Rule before = new NodeRule("destination", new OrRule(Lists.of(
                definition,
                value
        )));

        Rule source = new NodeRule("source", value);
        return new TypeRule("assignment", LocatingRule.First(before, "=", source));
    }
}
