package magmac.app.lang.node;

/**
 * Represents a single case value in a switch statement. A sample syntax is
 * {@code case 1;}, where the value {@code 1} is captured by this node.
 */

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Destructors;
import magmac.app.lang.java.JavaDeserializers;
import magmac.app.lang.java.JavaLang;

public record SingleCaseValue(JavaLang.Value value) implements CaseValue {
    public static Option<CompileResult<SingleCaseValue>> deserialize(Node node) {
        return Destructors.destructWithType("case-single", node).map(destructor -> destructor.withNode("value", JavaDeserializers::deserializeValueOrError)
                .complete(SingleCaseValue::new));
    }

    public static TypeRule createRule(Rule value) {
        return new TypeRule("case-single", new NodeRule("value", new StripRule(new SuffixRule(value, ";"))));
    }
}
