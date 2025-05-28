package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDeserializer;
import magmac.app.compile.node.Node;

record MethodNode(Definition definition, List<FunctionSegment> children, List<Parameter> right) implements ClassMember {
    public static Option<CompileResult<ClassMember>> deserialize(Node node) {
        return node.deserializeWithType("method").map((InitialDeserializer deserializer) -> deserializer
                .withNode("header", Definition::deserializeError)
                .nodeList("children", FunctionSegment::deserialize)
                .nodeList("parameters", Parameters::deserialize)
                .complete((tuple) -> new MethodNode(tuple.left().left(), tuple.left().right(), tuple.right()))
                .mapValue((MethodNode type) -> type));
    }
}
