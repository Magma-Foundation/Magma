package magmac.app.lang.node;

/**
 * Helpers for turning {@link Node} structures into assignable values. For
 * example the left hand side of {@code a = 5} is deserialized using this class.
 */

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;
import magmac.app.lang.java.JavaDeserializers;
import magmac.app.lang.java.JavaLang;

public final class Assignables {
    public static CompileResult<JavaLang.Assignable> deserializeError(Node node) {
        return Deserializers.orError("assignable", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeTypedDefinition),
                Deserializers.wrap(JavaDeserializers::deserializeValue)
        ));
    }
}
