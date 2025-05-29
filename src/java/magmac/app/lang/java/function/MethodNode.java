package magmac.app.lang.java.function;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDeserializer;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.define.Definition;
import magmac.app.lang.java.structure.ClassMember;

public record MethodNode(Definition definition, List<FunctionSegment> children, List<Parameter> right) implements ClassMember {
    public static Option<CompileResult<ClassMember>> deserialize(Node node) {
        return node.deserializeWithType("method").map((InitialDeserializer deserializer) -> deserializer
                .withNode("header", Definition::deserializeError)
                .withNodeList("children", FunctionSegment::deserialize)
                .withNodeList("parameters", Parameters::deserialize)
                .complete((tuple) -> new MethodNode(tuple.left().left(), tuple.left().right(), tuple.right()))
                .mapValue((MethodNode type) -> type));
    }
}
