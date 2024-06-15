package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;

public class MagmaGenerator extends Generator {
    @Override
    protected Tuple<Node, Integer> postVisit(Node node, int depth) {
        if (!node.is("function")) return new Tuple<>(node, depth);

        return new Tuple<>(node.mapAttributes(attributes -> {
            return attributes.mapValue("definition", NodeAttribute.Factory, definition -> {
                return definition.mapAttributes(attributes1 -> {
                    return attributes1.mapValue("modifiers", StringListAttribute.Factory, list -> {
                        if (list.contains("class")) return list;

                        var copy = new ArrayList<String>();
                        if (list.contains("public")) {
                            copy.add("public");
                        }

                        copy.add("def");
                        return copy;
                    });
                });
            });

        }), depth);
    }
}
