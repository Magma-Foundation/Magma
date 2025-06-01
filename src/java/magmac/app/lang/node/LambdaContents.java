package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

public class LambdaContents {
    public static CompileResult<LambdaContent> deserialize(Node node) {
        return Deserializers.orError("lambda-content", node, Lists.of(
                Deserializers.wrap(ValueContent::deserialize),
                Deserializers.wrap(BlockContent::deserialize)
        ));
    }
}
