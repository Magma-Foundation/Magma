package magmac.app.lang.node;

import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

public class StructureStatementValues {
    public static CompileResult<StructureStatementValue> deserialize(Node node) {
        return Deserializers.orError("structure-statement-value", node, Lists.of(
                Deserializers.wrap(JavaDefinition::deserializeTyped),
                Deserializers.wrap(AssignmentNode::deserialize)
        ));
    }
}
