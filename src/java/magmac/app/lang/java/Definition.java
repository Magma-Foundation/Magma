package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDeserializer;
import magmac.app.compile.node.Node;

record Definition(String name, Type type, List<Modifier> modifiers) implements Parameter {
    public static CompileResult<Definition> deserializeError(Node node) {
        return Definition.complete(node.deserialize());
    }

    private static CompileResult<Definition> complete(InitialDeserializer deserialize) {
        return deserialize.withString("name")
                .node("type", Type::deserialize)
                .nodeList("modifiers", Modifier::deserialize)
                .complete(result -> new Definition(result.left().left(), result.left().right(), result.right()));
    }

    public static Option<CompileResult<Definition>> deserialize(Node node) {
        return node.deserializeWithType("definition").map(Definition::complete);
    }
}
