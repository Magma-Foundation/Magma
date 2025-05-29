package magmac.app.lang.java.structure;

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
import magmac.app.lang.LazyRule;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.assign.Assignment;

public record StructureStatement() implements StructureMember {
    public static Option<CompileResult<StructureMember>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "structure-statement").map(deserializer -> {
            return deserializer.complete(() -> new StructureStatement());
        });
    }

    public static Rule createStructureStatementRule(Rule definitionRule, LazyRule valueRule) {
        Rule definition = new NodeRule("value", new OrRule(Lists.of(
                definitionRule,
                Assignment.createAssignmentRule(definitionRule, valueRule))
        ));

        return new TypeRule("structure-statement", new StripRule(new SuffixRule(definition, ";")));
    }
}
