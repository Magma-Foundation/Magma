package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Serializable;

public record StructureValue<T, M>(
        String name,
        List<Modifier> modifiers,
        List<M> members,
        Option<List<TypeParam>> maybeTypeParams,
        Option<List<T>> maybeExtended,
        Option<List<T>> maybeImplemented
) {
}