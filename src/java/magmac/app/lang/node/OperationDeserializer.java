package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;
import magmac.app.lang.java.JavaDeserializers;
import magmac.app.lang.java.JavaLang.operation;

public record OperationDeserializer(Operator operator) implements TypedDeserializer<operation> {
    @Override
    public Option<CompileResult<operation>> deserialize(Node node) {
        return Destructors.destructWithType(this.operator().type(), node).map(deserializer -> deserializer.withNode("left", JavaDeserializers::deserializeValueOrError)
                .withNode("right", JavaDeserializers::deserializeValueOrError)
                .complete(tuple -> new operation(tuple.left(), this.operator(), tuple.right())));
    }
}