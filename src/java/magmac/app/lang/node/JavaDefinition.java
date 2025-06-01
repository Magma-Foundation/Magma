package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public record JavaDefinition(Definition<JavaType> definition)
        implements JavaParameter, Assignable, JavaMethodHeader, StructureStatementValue, LambdaParameter {
    public static CompileResult<JavaDefinition> deserialize(Node node) {
        return Definition.deserialize(node).mapValue(JavaDefinition::new);
    }

    public static Option<CompileResult<JavaDefinition>> deserializeTyped(Node node) {
        return Definition.deserializeWithType(node).map(value -> value.mapValue(JavaDefinition::new));
    }
}
