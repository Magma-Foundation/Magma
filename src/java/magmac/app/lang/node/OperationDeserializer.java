package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;
import magmac.app.lang.java.JavaDeserializers;
import magmac.app.lang.java.JavaLang.JavaOperation;

public record OperationDeserializer(Operator operator) implements TypedDeserializer<JavaOperation> {
    @Override
    public Option<CompileResult<JavaOperation>> deserialize(Node node) {
        return Destructors.destructWithType(this.operator().type(), node).map(deserializer -> deserializer.withNode("left", JavaDeserializers::deserializeJavaOrError)
                .withNode("right", JavaDeserializers::deserializeJavaOrError)
                .complete(tuple -> new JavaOperation(tuple.left(), this.operator(), tuple.right())));
    }
}