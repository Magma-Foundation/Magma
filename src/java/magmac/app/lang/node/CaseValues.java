package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

public final class CaseValues {
    public static CompileResult<CaseValue> deserializeOrError(Node node) {
        return Deserializers.orError("case-value", node, Lists.of(
                Deserializers.wrap(SingleCaseValue::deserialize),
                Deserializers.wrap(MultipleCaseValue::deserialize)
        ));
    }
}
