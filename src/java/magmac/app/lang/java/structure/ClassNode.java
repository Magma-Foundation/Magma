package magmac.app.lang.java.structure;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDeserializer;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.define.Modifier;
import magmac.app.lang.java.root.JavaRootSegment;

public record ClassNode(String name, List<Modifier> modifiers, List<ClassMember> members) implements JavaRootSegment {
    public static Option<CompileResult<JavaRootSegment>> deserialize(Node node) {
        return node.deserializeWithType("class").map((InitialDeserializer deserializer) -> deserializer
                .withString("name")
                .withNodeList("modifiers", Modifier::deserialize)
                .withNodeList("children", ClassMembers::deserialize)
                .complete(tuple -> new ClassNode(tuple.left().left(), tuple.left().right(), tuple.right())));
    }
}
