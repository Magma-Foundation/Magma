package magmac.app.lang.node;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

public record CaseDefinition(String name) {
    public static CompileResult<CaseDefinition> deserialize(Node node) {
        return Destructors.destruct(node)
                .withString("name")
                .complete(CaseDefinition::new);
    }
}
