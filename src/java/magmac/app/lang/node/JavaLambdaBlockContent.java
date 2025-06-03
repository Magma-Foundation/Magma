package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

public record JavaLambdaBlockContent(List<JavaFunctionSegment> children) implements JavaLambdaContent {
    public static Option<CompileResult<JavaLambdaBlockContent>> deserialize(Node node) {
        return Destructors.destructWithType("block", node).map(destructor -> {
            return destructor.withNodeList("children", FunctionSegments::deserialize)
                    .complete(JavaLambdaBlockContent::new);
        });
    }
}
