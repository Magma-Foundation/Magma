package magmac.app.lang.java;

import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

record Definition(String name, Type type, List<Modifier> modifiers) {
    public static CompileResult<Definition> deserialize(Node node) {
        return node.deserialize()
                .string("name")
                .node("type", Type::deserialize)
                .nodeList("modifiers", Modifier::deserialize)
                .complete(result -> new Definition(result.left().left(), result.left().right(), result.right()));
    }
}
