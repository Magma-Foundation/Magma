package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;
import magmac.app.lang.JavaLang;

public class JavaCallers {
    public static CompileResult<JavaLang.Caller> deserialize(Node node) {
        return Deserializers.orError("caller", node, Lists.of(
                Deserializers.wrap(Construction::deserialize),
                Deserializers.wrap(Values::deserialize)
        ));
    }
}
