package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.CompoundDeserializer;
import magmac.app.compile.node.InitialDeserializer;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

public record StructureNode(
        StructureType type,
        String name,
        List<Modifier> modifiers,
        List<StructureMember> members,
        Option<List<Type>> implemented,
        Option<List<TypeParam>> typeParams,
        Option<List<Parameter>> parameters,
        Option<List<Type>> extended,
        Option<List<Type>> variants
) implements JavaRootSegment {
    public static Option<CompileResult<JavaRootSegment>> deserialize(StructureType type, Node node) {
        return Deserializers.deserializeWithType(node, type.name().toLowerCase())
                .map((InitialDeserializer deserializer) -> StructureNode.deserializeHelper(type, deserializer));
    }

    private static CompileResult<JavaRootSegment> deserializeHelper(StructureType type, InitialDeserializer deserializer) {
        return StructureNode.attachOptionals(StructureNode.attachRequired(deserializer)).complete(tuple -> StructureNode.from(type, tuple));
    }

    private static CompoundDeserializer<Tuple2<Tuple2<String, List<Modifier>>, List<StructureMember>>> attachRequired(InitialDeserializer deserializer) {
        return deserializer.withString("name")
                .withNodeList("modifiers", Modifier::deserialize)
                .withNodeList("children", StructureMembers::deserialize);
    }

    private static CompoundDeserializer<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<StructureMember>>, Option<List<Type>>>, Option<List<TypeParam>>>, Option<List<Parameter>>>, Option<List<Type>>>, Option<List<Type>>>> attachOptionals(CompoundDeserializer<Tuple2<Tuple2<String, List<Modifier>>, List<StructureMember>>> attachRequired) {
        return attachRequired.withNodeListOptionally("implemented", Types::deserialize)
                .withNodeListOptionally("type-parameters", TypeParam::deserialize)
                .withNodeListOptionally("parameters", Parameters::deserialize)
                .withNodeListOptionally("extended", Types::deserialize)
                .withNodeListOptionally("variants", Types::deserialize);
    }

    private static StructureNode from(
            StructureType type,
            Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<StructureMember>>, Option<List<Type>>>, Option<List<TypeParam>>>, Option<List<Parameter>>>, Option<List<Type>>>, Option<List<Type>>> tuple) {
        return new StructureNode(type,
                tuple.left().left().left().left().left().left().left(),
                tuple.left().left().left().left().left().left().right(),
                tuple.left().left().left().left().left().right(),
                tuple.left().left().left().left().right(),
                tuple.left().left().left().right(),
                tuple.left().left().right(),
                tuple.left().right(),
                tuple.right()
        );
    }
}
