package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.lang.Deserializers;

public record QualifiedType(List<Segment> segments) implements JavaBase {
    public static Option<CompileResult<JavaBase>> deserializeQualified(Node node) {
        return Deserializers.deserializeWithType("qualified", node).map(deserializer -> deserializer.withNodeList("segments", Segment::deserialize).complete(QualifiedType::new));
    }

    public static TypeRule createQualifiedRule() {
        return new TypeRule("qualified", QualifiedType.createSegmentsRule("segments"));
    }

    private static Rule createSegmentsRule(String key) {
        return NodeListRule.createNodeListRule(key, new DelimitedFolder('.'), Symbols.createSymbolRule("value"));
    }

    @Override
    public Node serialize() {
        return new MapNode("qualified").withNodeListAndSerializer("segments", this.segments, Segment::serialize);
    }
}
