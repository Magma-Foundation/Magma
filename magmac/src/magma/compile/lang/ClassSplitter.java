package magma.compile.lang;

import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

public class ClassSplitter extends Modifier {
    @Override
    protected Node postVisit(Node node) {
        if(!node.is("block")) return node;

        return node.mapAttributes(attributes -> attributes.mapValue("children", NodeListAttribute.Factory, children -> {
            return children.stream()
                    .map(child -> {
                        if(child.is("class")) {
                            return child.retype("function");
                        } else {
                            return child;
                        }
                    })
                    .toList();
        }));
    }
}
