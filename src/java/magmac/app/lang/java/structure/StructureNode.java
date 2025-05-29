package magmac.app.lang.java.structure;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDeserializer;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Type;
import magmac.app.lang.java.define.Modifier;
import magmac.app.lang.java.function.Parameter;
import magmac.app.lang.java.function.Parameters;
import magmac.app.lang.java.root.JavaRootSegment;
import magmac.app.lang.java.type.Types;

public record StructureNode(
        StructureType type,
        String name,
        List<Modifier> modifiers,
        List<StructureMember> members,
        Option<List<Type>> implemented,
        Option<List<TypeParam>> typeParams,
        Option<List<Parameter>> parameters
) implements JavaRootSegment {
    public static Option<CompileResult<JavaRootSegment>> deserialize(StructureType type, Node node) {
        return node.deserializeWithType(type.name().toLowerCase()).map((InitialDeserializer deserializer) -> deserializer
                .withString("name")
                .withNodeList("modifiers", Modifier::deserialize)
                .withNodeList("children", StructureMembers::deserialize)
                .withNodeListOptionally("implemented", Types::deserialize)
                .withNodeListOptionally("type-parameters", TypeParam::deserialize)
                .withNodeListOptionally("parameters", Parameters::deserialize)
                .complete(tuple -> StructureNode.from(type, tuple)));
    }

    private static StructureNode from(
            StructureType type,
            Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<StructureMember>>, Option<List<Type>>>, Option<List<TypeParam>>>, Option<List<Parameter>>> tuple) {
        return new StructureNode(type,
                tuple.left().left().left().left().left(),
                tuple.left().left().left().left().right(),
                tuple.left().left().left().right(),
                tuple.left().left().right(),
                tuple.left().right(),
                tuple.right()
        );
    }
}
