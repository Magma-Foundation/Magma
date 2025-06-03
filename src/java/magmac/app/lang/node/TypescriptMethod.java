package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public record TypescriptMethod(
        ParameterizedMethodHeader<TypeScriptParameter> header,
        Option<List<TypescriptFunctionSegment>> maybeChildren
) implements TypescriptStructureMember {
    @Override
    public Node serialize() {
        Node node = new MapNode("method")
                .withNodeSerialized("header", this.header);

        return this.maybeChildren.map(children -> node.withNodeListSerialized("children", children)).orElse(node);
    }
}
