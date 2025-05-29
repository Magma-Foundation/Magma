package magmac.app.lang.java.value;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Segment;
import magmac.app.lang.java.type.Base;

public record Qualified(List<Segment> segments) implements Base {
    public static Option<CompileResult<Base>> deserializeQualified(Node node) {
        return node.deserializeWithType("qualified").map(deserializer -> {
            return deserializer.withNodeList("segments", Segment::deserialize).complete(Qualified::new);
        });
    }
}
