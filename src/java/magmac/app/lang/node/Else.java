package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

class Else implements BlockHeader {
    public static Option<CompileResult<Else>> deserialize(Node node) {
        return Deserializers.deserializeWithType("else", node).map(deserializer -> deserializer.complete(Else::new));
    }
}
