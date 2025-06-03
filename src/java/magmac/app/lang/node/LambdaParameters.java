package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;
import magmac.app.lang.java.JavaDefinition;

public class LambdaParameters {
    public static CompileResult<LambdaParameter> deserialize(Node node) {
        return Deserializers.orError("lambda-parameter", node, Lists.of(
                Deserializers.wrap(Symbols::deserialize),
                Deserializers.wrap(JavaDefinition::deserializeTyped)
        ));
    }
}
