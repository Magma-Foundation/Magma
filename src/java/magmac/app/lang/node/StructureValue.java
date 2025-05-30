package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Serializable;

public record StructureValue<T extends Serializable>(
        String name,
        List<Modifier> modifiers,
        List<T> members,
        Option<List<TypeParam>> maybeTypeParams,
        Option<List<Type>> maybeExtended,
        Option<List<Type>> maybeImplemented
) {
    public Node serialize(String type) {
        return new MapNode(type)
                .withString("name", this.name)
                .withNodeListSerialized("modifiers", this.modifiers, Serializable::serialize)
                .withNodeListSerialized("members", this.members, Serializable::serialize)
                .merge(this.serializeTypeParams())
                .merge(this.serializeExtendedParams())
                .merge(this.serializeImplementsParams());
    }

    private Node serializeImplementsParams() {
        return this.maybeImplemented
                .map(implemented -> new MapNode().withNodeListSerialized("implemented", implemented, Serializable::serialize))
                .orElse(new MapNode());
    }

    private Node serializeExtendedParams() {
        return this.maybeExtended
                .map(extended -> new MapNode().withNodeListSerialized("extended", extended, Serializable::serialize))
                .orElse(new MapNode());
    }

    private Node serializeTypeParams() {
        return this.maybeTypeParams
                .map(typeParams -> new MapNode().withNodeListSerialized("type-parameters", typeParams, Serializable::serialize))
                .orElse(new MapNode());
    }
}