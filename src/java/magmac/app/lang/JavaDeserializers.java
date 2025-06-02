package magmac.app.lang;

import magmac.api.Option;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.node.JavaTypes;
import magmac.app.lang.node.Values;

public final class JavaDeserializers {
    public static CompileResult<JavaLang.Caller> deserialize(Node node) {
        return Deserializers.orError("caller", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeConstruction),
                Deserializers.wrap(Values::deserialize)
        ));
    }

    public static Option<CompileResult<JavaLang.Caller>> deserializeConstruction(Node node) {
        return Destructors.destructWithType("construction", node)
                .map(deserializer -> deserializer.withNode("type", JavaTypes::deserialize)
                        .complete(JavaLang.Construction::new));
    }
}
