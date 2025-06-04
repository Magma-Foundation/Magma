package magmac.app.lang.node;

/**
 * Represents a case body containing multiple statements.
 * Example syntax:
 * {@code case 1 {
 *     doSomething();
 * }}
 */

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.StatementFolder;
import magmac.app.lang.Destructors;
import magmac.app.lang.java.JavaFunctionSegment;

public record MultipleCaseValue(List<JavaFunctionSegment> children) implements CaseValue {
    public static Option<CompileResult<CaseValue>> deserialize(Node node) {
        return Destructors.destructWithType("case-multiple", node).map(destructor -> destructor.withNodeList("children", FunctionSegments::deserialize)
                .complete(MultipleCaseValue::new));
    }

    public static TypeRule createRule(Rule segment) {
        return new TypeRule("case-multiple", new StripRule(new PrefixRule("{", new SuffixRule(NodeListRule.createNodeListRule("children", new StatementFolder(), segment), "}"))));
    }
}
