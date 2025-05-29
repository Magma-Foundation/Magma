package magmac.app.lang.node;

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
import magmac.app.lang.Deserializers;

interface BlockHeader {
    static CompileResult<BlockHeader> deserialize(Node node) {
        return Deserializers.orError("header", node, Lists.of(
                Deserializers.wrap(node1 -> Conditional.deserialize(ConditionalType.If, node1)),
                Deserializers.wrap(node1 -> Conditional.deserialize(ConditionalType.While, node1)),
                Deserializers.wrap(Else::deserialize),
                Deserializers.wrap(Try::deserialize),
                Deserializers.wrap(Catch::deserialize)
        ));
    }

    static Rule createBlockHeaderRule(Rule value, Rule definition) {
        return new OrRule(Lists.of(
                new TypeRule("else", new StripRule(new ExactRule("else"))),
                new TypeRule("try", new StripRule(new ExactRule("try"))),
                Conditional.createConditionalRule("if", value),
                Conditional.createConditionalRule("while", value),
                new TypeRule("catch", new StripRule(new PrefixRule("catch", new StripRule(new PrefixRule("(", new SuffixRule(new NodeRule("definition", definition), ")"))))))
        ));
    }
}
