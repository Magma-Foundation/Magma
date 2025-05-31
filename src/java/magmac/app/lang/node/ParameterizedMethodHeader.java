package magmac.app.lang.node;

import magmac.api.collect.list.List;
import magmac.app.compile.node.Node;
import magmac.app.lang.Serializable;

public record ParameterizedMethodHeader<T extends Serializable>(
        TypeScriptMethodHeader header,
        List<T> parameters
) implements Serializable {
    @Override
    public Node serialize() {
        return this.header.serialize()
                .withNodeListSerialized("parameters", this.parameters);
    }
}
