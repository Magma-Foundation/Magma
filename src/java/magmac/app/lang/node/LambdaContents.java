package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;
import magmac.app.lang.JavaRules;

public class LambdaContents {
    public static CompileResult<JavaLambdaContent> deserialize(Node node) {
        return Deserializers.orError("lambda-content", node, Lists.of(
                Deserializers.wrap(JavaRules::deserializeLambdaValueContent),
                Deserializers.wrap(JavaLambdaBlockContent::deserialize)
        ));
    }
}
