package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

public record SingleHeader(String name) implements LambdaHeader {
    public static Option<CompileResult<LambdaHeader>> deserialize(Node node) {
        return Deserializers.deserializeWithType("single", node).map(destructor -> {
            return destructor.withString("name").complete(SingleHeader::new);
        });
    }
}
