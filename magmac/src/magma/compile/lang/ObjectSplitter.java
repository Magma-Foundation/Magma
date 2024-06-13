package magma.compile.lang;

import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

import java.util.stream.Stream;

public class ObjectSplitter extends Modifier {
    @Override
    protected Node preVisit(Node node) {
        if (node.is("block")) {
            return node.mapAttributes(attributes -> attributes.mapValue("children", NodeListAttribute.Factory, children -> children.stream()
                    .flatMap(this::split)
                    .toList()));
        }

        return node;
    }

    private Stream<Node> split(Node child) {
        if (child.is("class")) {
            return Stream.of(child);
        } else {
            return Stream.of(child);
        }
    }
}
