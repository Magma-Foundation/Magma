package magmac.app.lang.node;

/**
 * Deserializes the body of a lambda expression. It can be either a single value
 * like {@code x -> x + 1} or a block of statements.
 */

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;
import magmac.app.lang.JavaRules;
import magmac.app.lang.java.JavaLang;

public final class LambdaContents {
    public static CompileResult<JavaLang.JavaLambdaContent> deserialize(Node node) {
        return Deserializers.orError("lambda-content", node, Lists.of(
                Deserializers.wrap(JavaRules::deserializeLambdaValueContent),
                Deserializers.wrap(JavaLang.JavaLambdaBlockContent::deserialize)
        ));
    }
}
