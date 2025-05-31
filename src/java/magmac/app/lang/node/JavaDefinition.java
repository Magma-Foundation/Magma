package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public final class JavaDefinition implements Parameter, Assignable, MethodHeader {
    private final Definition<JavaType> definition;

    public JavaDefinition(Definition<JavaType> definition) {
        this.definition = definition;
    }

    public static CompileResult<JavaDefinition> deserialize(Node node) {
        return Definition.deserialize(node).mapValue(JavaDefinition::new);
    }

    public static Option<CompileResult<JavaDefinition>> deserializeTyped(Node node) {
        return Definition.deserializeWithType(node).map(value -> value.mapValue(JavaDefinition::new));
    }

    @Override
    public Node serialize() {
        return this.definition.serialize();
    }
}
