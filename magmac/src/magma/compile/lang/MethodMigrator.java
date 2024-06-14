package magma.compile.lang;

import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;

public class MethodMigrator extends Modifier {
    @Override
    protected Node postVisit(Node node) {
        if (node.is("method"))
            return node.retype("function").mapAttributes(attributes -> attributes.mapValue("modifiers", StringListAttribute.Factory, list -> {
                var copy = new ArrayList<>(list);
                copy.add("def");
                return copy;
            }));
        return node;
    }
}
