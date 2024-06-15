package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.Collections;

public class JavaNormalizer extends Generator {
    @Override
    protected Tuple<Node, Integer> postVisit(Node node, int depth) {
        if (node.is("declaration")) {
            return new Tuple<>(node.mapAttributes(attributes -> {
                var list = attributes.apply("modifiers")
                        .flatMap(Attribute::asStringList)
                        .orElse(Collections.emptyList());

                var copy = new ArrayList<String>();
                if (list.contains("final")) {
                    copy.add("const");
                } else {
                    copy.add("let");
                }

                return attributes.with("modifiers", new StringListAttribute(copy));
            }), depth);
        }

        return new Tuple<>(node, depth);
    }
}
