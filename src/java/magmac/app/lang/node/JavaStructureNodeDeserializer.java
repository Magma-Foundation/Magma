package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.CompoundDestructor;
import magmac.app.compile.node.InitialDestructor;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

public record JavaStructureNodeDeserializer(JavaStructureType type) implements TypedDeserializer<JavaStructureNode> {
    private static CompileResult<JavaStructureNode> deserializeHelper(JavaStructureType type, InitialDestructor deserializer) {
        return JavaStructureNodeDeserializer.attachOptionals(JavaStructureNodeDeserializer.attachRequired(deserializer))
                .complete(tuple -> JavaStructureNodeDeserializer.from(type, tuple));
    }

    private static CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>> attachRequired(InitialDestructor deserializer) {
        return deserializer.withString("name")
                .withNodeList("modifiers", Modifier::deserialize)
                .withNodeList("children", StructureMembers::deserialize);
    }

    private static CompoundDestructor<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>>> attachOptionals(CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>> attachRequired) {
        return attachRequired.withNodeListOptionally("implemented", Types::deserialize)
                .withNodeListOptionally("type-parameters", TypeParam::deserialize)
                .withNodeListOptionally("parameters", Parameters::deserialize)
                .withNodeListOptionally("extended", Types::deserialize)
                .withNodeListOptionally("variants", Types::deserialize);
    }

    private static JavaStructureNode from(
            JavaStructureType type,
            Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>> tuple) {
        return new JavaStructureNode(type,
                new StructureValue<JavaType, JavaStructureMember>(tuple.left().left().left().left().left().left().left(), tuple.left().left().left().left().left().left().right(), tuple.left().left().left().left().left().right(), tuple.left().left().left().right(), tuple.left().right(), tuple.left().left().left().left().right()), tuple.left().left().right(),
                tuple.right()
        );
    }

    public Option<CompileResult<JavaStructureNode>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, this.type().name().toLowerCase())
                .map((InitialDestructor deserializer) -> JavaStructureNodeDeserializer.deserializeHelper(this.type(), deserializer));
    }
}