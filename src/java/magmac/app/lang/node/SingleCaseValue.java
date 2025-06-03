package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Destructors;

public record SingleCaseValue(JavaValue value) implements CaseValue {
    public static Option<CompileResult<SingleCaseValue>> deserialize(Node node) {
        return Destructors.destructWithType("case-single", node).map(destructor -> {
            return destructor.withNode("value", Values::deserializeOrError)
                    .complete(SingleCaseValue::new);
        });
    }

    static TypeRule createRule(Rule value) {
        return new TypeRule("case-single", new NodeRule("value", new StripRule(new SuffixRule(value, ";"))));
    }
}
