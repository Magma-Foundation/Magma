package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

public record MultipleHeader(List<JavaParameter> parameters) implements LambdaHeader {
    public static Option<CompileResult<LambdaHeader>> deserialize(Node node) {
        return Destructors.destructWithType("multiple", node).map(destructor -> {
            return destructor.withNodeList("parameters", Parameters::deserialize).complete(MultipleHeader::new);
        });
    }
}
