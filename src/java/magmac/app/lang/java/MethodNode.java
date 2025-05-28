package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDeserializer;
import magmac.app.compile.node.Node;

record MethodNode(Definition definition, List<FunctionSegment> children) implements ClassMember {
    public static Option<CompileResult<ClassMember>> deserialize(Node node) {
        return node.deserializeWithType("method").map((InitialDeserializer deserializer) -> deserializer
                .withNode("header", (Node node1) -> Definition.deserialize(node1))
                .nodeList("children", FunctionSegment::deserialize)
                .complete((tuple) -> new MethodNode(tuple.left(), tuple.right()))
                .mapValue((MethodNode type) -> type));
    }
}
