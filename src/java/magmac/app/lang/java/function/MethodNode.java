package magmac.app.lang.java.function;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDeserializer;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.structure.StructureMember;

public record MethodNode(MethodHeader methodHeader, Option<List<FunctionSegment>> maybeChildren, List<Parameter> right) implements StructureMember {
    public static Option<CompileResult<StructureMember>> deserialize(Node node) {
        return node.deserializeWithType("method").map((InitialDeserializer deserializer) -> deserializer
                .withNode("header", MethodHeader::deserializeError)
                .withNodeListOptionally("children", FunctionSegment::deserialize)
                .withNodeList("parameters", Parameters::deserialize)
                .complete((tuple) -> new MethodNode(tuple.left().left(), tuple.left().right(), tuple.right()))
                .mapValue((MethodNode type) -> type));
    }
}
