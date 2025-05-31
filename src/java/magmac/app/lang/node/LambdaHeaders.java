package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

public class LambdaHeaders {
    public static CompileResult<LambdaHeader> deserialize(Node node) {
        return Deserializers.orError("lambda-header", node, Lists.of(
                Deserializers.wrap(SingleHeader::deserialize)
        ));
    }
}
