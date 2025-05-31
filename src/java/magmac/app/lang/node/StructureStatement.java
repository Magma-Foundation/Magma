package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Deserializers;
import magmac.app.lang.LazyRule;

public record StructureStatement() implements JavaStructureMember {
    public static Option<CompileResult<JavaStructureMember>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "structure-statement").map(deserializer -> deserializer.complete(StructureStatement::new));
    }

    public static Rule createStructureStatementRule(Rule definitionRule, LazyRule valueRule) {
        Rule definition = new NodeRule("value", new OrRule(Lists.of(
                definitionRule,
                AssignmentNode.createAssignmentRule(definitionRule, valueRule))
        ));

        return new TypeRule("structure-statement", new StripRule(new SuffixRule(definition, ";")));
    }
}
