package magmac.app.lang.java;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.EmptyDestroyer;
import magmac.app.compile.node.Node;

record MethodNode(Definition definition) implements ClassMember {
    public static Option<CompileResult<ClassMember>> deserialize(Node node) {
        return node.deserializeWithType("method").map((EmptyDestroyer deserializer) -> deserializer.node("definition", (Node node1) -> Definition.deserialize(node1))
                .complete((Definition definition1) -> new MethodNode(definition1))
                .mapValue((MethodNode type) -> type));
    }
}
