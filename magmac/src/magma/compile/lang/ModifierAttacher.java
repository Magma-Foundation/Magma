package magma.compile.lang;

import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;

public class ModifierAttacher extends Modifier {
    @Override
    protected Node postVisit(Node node) {
        if (node.is("declaration")) {
            return node.mapAttributes(attributes -> {
                return attributes.mapValue("modifiers", StringListAttribute.Factory, list -> {
                    var copy = new ArrayList<>(list);
                    if (copy.contains("final")) {
                        copy.remove("final");
                        copy.add("const");
                    } else {
                        copy.add("let");
                    }

                    return copy;
                });
            });
        }

        return node;
    }
}
