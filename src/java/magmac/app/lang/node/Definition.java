package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDestructor;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;
import magmac.app.lang.Serializable;

public record Definition<T>(
        Option<List<Annotation>> maybeAnnotations,
        List<Modifier> modifiers,
        String name,
        Option<List<TypeParam>> maybeTypeParams,
        T type
) {
    static CompileResult<Definition<JavaType>> deserialize0(InitialDestructor deserialize) {
        return deserialize.withString("name")
                .withNode("type", JavaTypes::deserialize)
                .withNodeList("modifiers", Modifier::deserialize)
                .withNodeListOptionally("annotations", Annotation::deserialize)
                .withNodeListOptionally("type-parameters", TypeParam::deserialize)
                .complete((result) -> new Definition<JavaType>(result.left().right(), result.left().left().right(), result.left().left().left().left(), result.right(), result.left().left().left().right()));
    }

    static CompileResult<Definition<JavaType>> deserialize(Node node) {
        return Definition.deserialize0(Destructors.destruct(node));
    }

    static Option<CompileResult<Definition<JavaType>>> deserializeWithType(Node node) {
        return Destructors.destructWithType("definition", node)
                .map(Definition::deserialize0);
    }

    public <T extends Serializable> Definition<T> withType(T newType) {
        return new Definition<T>(this.maybeAnnotations, this.modifiers, this.name, this.maybeTypeParams, newType);
    }

    public Definition<T> withName(String name) {
        return new Definition<>(this.maybeAnnotations, this.modifiers, name, this.maybeTypeParams, this.type);
    }
}