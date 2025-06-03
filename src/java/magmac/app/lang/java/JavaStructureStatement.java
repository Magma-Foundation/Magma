package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Destructors;
import magmac.app.lang.JavaDeserializers;
import magmac.app.lang.LazyRule;
import magmac.app.lang.node.StructureStatementValue;

public record JavaStructureStatement(StructureStatementValue value) implements JavaStructureMember {
    public static Option<CompileResult<JavaStructureMember>> deserialize(Node node) {
        return Destructors.destructWithType("structure-statement", node).map(deserializer -> deserializer
                .withNode("value", JavaDeserializers::deserializeStructureStatement)
                .complete(JavaStructureStatement::new));
    }

    public static Rule createStructureStatementRule(Rule definitionRule, LazyRule valueRule) {
        Rule definition = new NodeRule("value", new OrRule(Lists.of(
                definitionRule,
                JavaAssignmentNode.createAssignmentRule(definitionRule, valueRule))
        ));

        return new TypeRule("structure-statement", new StripRule(new SuffixRule(definition, ";")));
    }
}
