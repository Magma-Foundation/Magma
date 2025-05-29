package magmac.app.lang.java.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.iter.Iters;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDeserializer;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.lang.java.Deserializers;

public record Namespaced(NamespacedType type, List<Segment> segments) implements JavaRootSegment {
    private static Option<CompileResult<JavaRootSegment>> deserialize(NamespacedType type, Node node) {
        return Deserializers.deserializeWithType(node, type.type()).map((InitialDeserializer deserializer) -> deserializer
                .withNodeList("segments", (Node node1) -> Segment.deserialize(node1))
                .complete((List<Segment> segments1) -> new Namespaced(type, segments1)));
    }

    public static Option<CompileResult<JavaRootSegment>> deserialize(Node node) {
        return Iters.fromValues(NamespacedType.values())
                .map((NamespacedType type) -> Namespaced.deserialize(type, node))
                .flatMap((Option<CompileResult<JavaRootSegment>> option) -> Iters.fromOption(option))
                .next();
    }

    public static Rule createNamespacedRule(String type) {
        Rule childRule = new NodeListRule("segments", new DelimitedFolder('.'), new StringRule("value"));
        return new TypeRule(type, new StripRule(new SuffixRule(new PrefixRule(type + " ", childRule), ";")));
    }
}
