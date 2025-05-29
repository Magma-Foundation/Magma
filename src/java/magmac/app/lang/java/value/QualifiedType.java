package magmac.app.lang.java.value;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.Segment;
import magmac.app.lang.java.type.Base;

public record QualifiedType(List<Segment> segments) implements Base {
    public static Option<CompileResult<Base>> deserializeQualified(Node node) {
        return Deserializers.deserializeWithType(node, "qualified").map(deserializer -> {
            return deserializer.withNodeList("segments", Segment::deserialize).complete(QualifiedType::new);
        });
    }

    public static TypeRule createQualifiedRule() {
        return new TypeRule("qualified", createSegmentsRule("segments"));
    }

    public static NodeListRule createSegmentsRule(String key) {
        return new NodeListRule(key, new DelimitedFolder('.'), Symbols.createSymbolRule("value"));
    }
}
