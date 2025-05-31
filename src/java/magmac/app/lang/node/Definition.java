package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDestructor;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;
import magmac.app.lang.Serializable;

public record Definition<T extends Serializable>(
        String name,
        T type,
        List<Modifier> modifiers,
        Option<List<Annotation>> maybeAnnotations,
        Option<List<TypeParam>> typeParams
) implements Serializable {
    static CompileResult<Definition<JavaType>> deserialize0(InitialDestructor deserialize) {
        return deserialize.withString("name")
                .withNode("type", Types::deserialize)
                .withNodeList("modifiers", Modifier::deserialize)
                .withNodeListOptionally("annotations", Annotation::deserialize)
                .withNodeListOptionally("type-parameters", TypeParam::deserialize)
                .complete((result) -> new Definition<JavaType>(result.left().left().left().left(), result.left().left().left().right(), result.left().left().right(), result.left().right(), result.right()));
    }

    static CompileResult<Definition<JavaType>> deserialize(Node node) {
        return deserialize0(Deserializers.destruct(node));
    }

    static Option<CompileResult<Definition<JavaType>>> deserializeWithType(Node node) {
        return Deserializers.deserializeWithType(node, "definition")
                .map(Definition::deserialize0);
    }

    @Override
    public Node serialize() {
        return new MapNode("definition")
                .withString("name", this.name)
                .withNodeSerialized("type", this.type);
    }
}