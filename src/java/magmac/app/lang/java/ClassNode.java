package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDeserializer;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.root.JavaRootSegment;

record ClassNode(String name, List<ClassMember> members) implements JavaRootSegment {
    public static Option<CompileResult<JavaRootSegment>> deserialize(Node node) {
        return node.deserializeWithType("class").map((InitialDeserializer deserializer) -> deserializer
                .withString("name")
                .nodeList("children", (Node node1) -> ClassMembers.deserialize(node1))
                .complete((Tuple2<String, List<ClassMember>> tuple) -> new ClassNode(tuple.left(), tuple.right())));
    }
}
