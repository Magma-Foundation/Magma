package magmac.app.lang.node;

import magmac.api.collect.list.List;
import magmac.app.compile.node.Node;
import magmac.app.lang.Serializable;
import magmac.app.lang.web.TypescriptLang;

public record ParameterizedMethodHeader<T extends Serializable>(
        TypescriptLang.TypeScriptMethodHeader header,
        List<T> parameters
) implements Serializable {
    @Override
    public Node serialize() {
        return this.header.serialize()
                .withNodeListSerialized("parameters", this.parameters);
    }
}
