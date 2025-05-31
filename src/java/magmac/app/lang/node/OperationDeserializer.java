package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

public record OperationDeserializer(Operator operator) implements TypedDeserializer<Operation> {
    @Override
    public Option<CompileResult<Operation>> deserialize(Node node) {
        return Destructors.destructWithType(this.operator().type(), node).map(deserializer -> deserializer.withNode("left", Values::deserializeOrError)
                .withNode("right", Values::deserializeOrError)
                .complete(tuple -> new Operation(tuple.left(), this.operator(), tuple.right())));
    }
}