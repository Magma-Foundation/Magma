package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

class Try implements BlockHeader {
    public static Option<CompileResult<Try>> deserialize(Node node) {
        return Destructors.destructWithType("try", node).map(deserializer -> deserializer.complete(Try::new));
    }
}
