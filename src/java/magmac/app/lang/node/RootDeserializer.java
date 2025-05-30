package magmac.app.lang.node;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;
import magmac.app.lang.Serializable;

public record RootDeserializer<T extends Serializable>(
        Deserializer<T> deserializer) implements Deserializer<Root<T>> {
    @Override
    public CompileResult<Root<T>> deserialize(Node node) {
        return Deserializers.destruct(node)
                .withNodeList("children", this.deserializer)
                .complete(Root::new);
    }
}