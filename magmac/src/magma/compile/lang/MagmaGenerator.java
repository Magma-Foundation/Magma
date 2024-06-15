package magma.compile.lang;

import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;

public class MagmaGenerator extends Generator {
    @Override
    protected Node postVisit(Node node, int depth) {
        if (!node.is("function")) return node;

        return node.mapAttributes(attributes -> attributes.mapValue("modifiers", StringListAttribute.Factory, list -> {
            var copy = new ArrayList<String>();
            if (list.contains("public")) {
                copy.add("export");
            }

            copy.add("def");
            return copy;
        }));
    }
}
