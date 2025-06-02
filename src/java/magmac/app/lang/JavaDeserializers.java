package magmac.app.lang;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.node.Construction;
import magmac.app.lang.node.Values;

public final class JavaDeserializers {
    public static CompileResult<JavaLang.Caller> deserialize(Node node) {
        return Deserializers.orError("caller", node, Lists.of(
                Deserializers.wrap(Construction::deserialize),
                Deserializers.wrap(Values::deserialize)
        ));
    }
}
