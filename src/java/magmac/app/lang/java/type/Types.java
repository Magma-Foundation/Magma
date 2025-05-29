package magmac.app.lang.java.type;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.Type;
import magmac.app.lang.java.value.Symbols;

public class Types {
    public static CompileResult<Type> deserialize(Node node) {
        return Deserializers.orError("type", node, Lists.of(
                Deserializers.wrap(Symbols::deserialize),
                Deserializers.wrap(TemplateType::deserialize)
        ));
    }
}
