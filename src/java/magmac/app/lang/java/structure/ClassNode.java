package magmac.app.lang.java.structure;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDeserializer;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Type;
import magmac.app.lang.java.define.Modifier;
import magmac.app.lang.java.root.JavaRootSegment;
import magmac.app.lang.java.type.Types;

public record ClassNode(
        String name,
        List<Modifier> modifiers,
        List<ClassMember> members,
        Option<List<Type>> implemented
) implements JavaRootSegment {
    public static Option<CompileResult<JavaRootSegment>> deserialize(Node node) {
        return node.deserializeWithType("class").map((InitialDeserializer deserializer) -> deserializer
                .withString("name")
                .withNodeList("modifiers", Modifier::deserialize)
                .withNodeList("children", ClassMembers::deserialize)
                .withNodeListOptionally("implemented", Types::deserialize)
                .complete(ClassNode::from));
    }

    private static ClassNode from(Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<ClassMember>>, Option<List<Type>>> tuple) {
        return new ClassNode(tuple.left().left().left(), tuple.left().left().right(), tuple.left().right(), tuple.right());
    }
}
