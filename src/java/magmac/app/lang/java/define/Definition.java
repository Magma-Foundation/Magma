package magmac.app.lang.java.define;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDeserializer;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Type;
import magmac.app.lang.java.assign.Assignable;
import magmac.app.lang.java.function.MethodHeader;
import magmac.app.lang.java.function.Parameter;
import magmac.app.lang.java.structure.TypeParam;
import magmac.app.lang.java.type.Types;

public record Definition(
        String name,
        Type type,
        List<Modifier> modifiers,
        Option<List<Annotation>> annotations,
        Option<List<TypeParam>> typeParams
) implements Parameter, Assignable, MethodHeader {
    public static CompileResult<Definition> deserializeError(Node node) {
        return Definition.complete(node.deserialize());
    }

    private static CompileResult<Definition> complete(InitialDeserializer deserialize) {
        return deserialize.withString("name")
                .withNode("type", Types::deserialize)
                .withNodeList("modifiers", Modifier::deserialize)
                .withNodeListOptionally("annotations", Annotation::deserialize)
                .withNodeListOptionally("type-parameters", TypeParam::deserialize)
                .complete((result) -> new Definition(
                        result.left().left().left().left(),
                        result.left().left().left().right(),
                        result.left().left().right(),
                        result.left().right(),
                        result.right()));
    }

    public static Option<CompileResult<Definition>> deserialize(Node node) {
        return node.deserializeWithType("definition").map(Definition::complete);
    }
}
