package magma.compile.lang;

import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;

public class MagmaGenerator extends Generator {
    @Override
    protected Node postVisit(Node node) {
        if (!node.is("function")) return node;

        return node.mapAttributes(attributes -> attributes.mapValue("modifiers", StringListAttribute.Factory, list -> {
            var copy = new ArrayList<>(list);
            copy.add("def");
            return copy;
        }));
    }
}
