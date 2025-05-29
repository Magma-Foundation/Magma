package magmac.app.lang.java.function;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public record Block(List<FunctionSegment> segments, BlockHeader header) implements FunctionSegment {
    public static Option<CompileResult<Block>> deserialize(Node node) {
        return node.deserializeWithType("block").map(deserializer -> {
            return deserializer
                    .withNodeList("children", FunctionSegment::deserialize)
                    .withNode("header", BlockHeader::deserialize)
                    .complete(tuple -> new Block(tuple.left(), tuple.right()));
        });
    }
}
