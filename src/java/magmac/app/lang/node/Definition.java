package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Serializable;

public record Definition(
        String name,
        JavaType type,
        List<Modifier> modifiers,
        Option<List<Annotation>> maybeAnnotations,
        Option<List<TypeParam>> typeParams
) implements Serializable {
    @Override
    public Node serialize() {
        return new MapNode("definition")
                .withString("name", this.name())
                .withNodeAndSerializer("type", this.type(), Serializable::serialize);
    }
}