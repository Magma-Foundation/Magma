package magmac.app.lang.node;

import magmac.api.collect.list.List;
import magmac.app.compile.node.Node;

public record ParameterizedMethodHeader(MethodHeader header, List<Parameter> parameters) implements MethodHeader {
    @Override
    public Node serialize() {
        return this.header.serialize().withNodeListSerialized("parameters", this.parameters);
    }
}
