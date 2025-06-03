package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Destructors;
import magmac.app.lang.node.Assignables;
import magmac.app.lang.node.StructureStatementValue;
import magmac.app.lang.web.TypescriptLang;

public record JavaAssignmentNode(JavaLang.JavaAssignable assignable, JavaLang.JavaValue value) implements JavaFunctionSegmentValue, StructureStatementValue, TypescriptLang.FunctionSegment.Value {
    public static Option<CompileResult<JavaAssignmentNode>> deserialize(Node node) {
        return Destructors.destructWithType("assignment", node).map(deserializer -> deserializer
                .withNode("destination", Assignables::deserializeError)
                .withNode("source", JavaDeserializers::deserializeJavaOrError)
                .complete(tuple -> new JavaAssignmentNode(tuple.left(), tuple.right())));
    }

    public static Rule createAssignmentRule(Rule definition, Rule value) {
        Rule before = new NodeRule("destination", new OrRule(Lists.of(
                definition,
                value
        )));

        Rule source = new NodeRule("source", value);
        return new TypeRule("assignment", LocatingRule.First(before, "=", source));
    }

    @Override
    public Node serialize() {
        return new MapNode("assignment");
    }
}
