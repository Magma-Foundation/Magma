package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.iter.Iters;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDestructor;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.lang.Destructors;

public record JavaNamespacedNode(NamespacedType type, List<Segment> segments) implements JavaRootSegment {
    private static Option<CompileResult<JavaRootSegment>> deserialize(NamespacedType type, Node node) {
        return Destructors.destructWithType(type.type(), node).map((InitialDestructor deserializer) -> deserializer
                .withNodeList("segments", Segment::deserialize)
                .complete((List<Segment> segments1) -> new JavaNamespacedNode(type, segments1)));
    }

    public static Option<CompileResult<JavaRootSegment>> deserialize(Node node) {
        return Iters.fromValues(NamespacedType.values())
                .map((NamespacedType type) -> JavaNamespacedNode.deserialize(type, node))
                .flatMap(Iters::fromOption)
                .next();
    }

    public static Rule createNamespacedRule(String type) {
        Rule childRule = NodeListRule.createNodeListRule("segments", new DelimitedFolder('.'), new StringRule("value"));
        return new TypeRule(type, new StripRule(new SuffixRule(new PrefixRule(type + " ", childRule), ";")));
    }
}
