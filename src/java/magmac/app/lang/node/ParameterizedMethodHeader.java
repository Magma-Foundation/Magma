package magmac.app.lang.node;

/**
 * Header for a method that may contain generic type parameters. A simple
 * example is {@code <T> void foo(T value)} where {@code T} is captured in
 * {@code parameters}.
 */

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
