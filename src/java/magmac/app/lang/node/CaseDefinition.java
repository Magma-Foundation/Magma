package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

public record CaseDefinition(String name, Option<JavaType> type) {
    public static CompileResult<CaseDefinition> deserialize(Node node) {
        return Destructors.destruct(node)
                .withString("name")
                .withNodeOptionally("type", JavaTypes::deserialize)
                .complete(tuple -> new CaseDefinition(tuple.left(), tuple.right()));
    }
}
