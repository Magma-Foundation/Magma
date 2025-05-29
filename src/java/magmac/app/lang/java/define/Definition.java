package magmac.app.lang.java.define;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDeserializer;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Type;
import magmac.app.lang.java.type.Types;
import magmac.app.lang.java.assign.Assignable;
import magmac.app.lang.java.function.Parameter;

public record Definition(String name, Type type, List<Modifier> modifiers,
                         List<Annotation> annotations) implements Parameter, Assignable {
    public static CompileResult<Definition> deserializeError(Node node) {
        return Definition.complete(node.deserialize());
    }

    private static CompileResult<Definition> complete(InitialDeserializer deserialize) {
        return deserialize.withString("name")
                .withNode("type", Types::deserialize)
                .withNodeList("modifiers", Modifier::deserialize)
                .withNodeListOptionally("annotations", Annotation::deserialize)
                .complete(Definition::from);
    }

    private static Definition from(Tuple2<Tuple2<Tuple2<String, Type>, List<Modifier>>, Option<List<Annotation>>> result) {
        return new Definition(result.left().left().left(), result.left().left().right(), result.left().right(), result.right().orElse(Lists.empty()));
    }

    public static Option<CompileResult<Definition>> deserialize(Node node) {
        return node.deserializeWithType("definition").map(Definition::complete);
    }
}
