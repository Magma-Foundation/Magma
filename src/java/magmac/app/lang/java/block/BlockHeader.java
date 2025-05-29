package magmac.app.lang.java.block;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.function.Conditional;
import magmac.app.lang.java.function.ConditionalType;

public interface BlockHeader {
    static CompileResult<BlockHeader> deserialize(Node node) {
        return Deserializers.orError("header", node, Lists.of(
                Deserializers.wrap(node1 -> Conditional.deserialize(ConditionalType.If, node1)),
                Deserializers.wrap(node1 -> Conditional.deserialize(ConditionalType.While, node1)),
                Deserializers.wrap(Else::deserialize)
        ));
    }

    static Rule createBlockHeaderRule(Rule value, Rule definition) {
        return new OrRule(Lists.of(
                new TypeRule("else", new StripRule(new ExactRule("else"))),
                new TypeRule("try", new StripRule(new ExactRule("try"))),
                Conditional.createConditionalRule("if", value),
                Conditional.createConditionalRule("while", value),
                new StripRule(new PrefixRule("catch", new StripRule(new PrefixRule("(", new SuffixRule(new NodeRule("definition", definition), ")")))))
        ));
    }
}
