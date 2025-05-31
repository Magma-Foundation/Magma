package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

class Try implements BlockHeader {
    public static Option<CompileResult<Try>> deserialize(Node node) {
        return Deserializers.deserializeWithType("try", node).map(deserializer -> deserializer.complete(Try::new));
    }
}
