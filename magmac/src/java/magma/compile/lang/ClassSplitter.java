package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.Attributes;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.Collections;

public class ClassSplitter extends Generator {
    @Override
    protected Tuple<Node, Integer> postVisit(Node node, int depth) {
        if (!node.is("block")) return new Tuple<>(node, depth);

        return new Tuple<>(node.mapAttributes(ClassSplitter::mapBlockChildren), depth);
    }

    private static Attributes mapBlockChildren(Attributes attributes) {
        return attributes.mapValue("children", NodeListAttribute.Factory, children -> children.stream()
                .map(ClassSplitter::mapBlockChild)
                .toList());
    }

    private static Node mapBlockChild(Node child) {
        if (child.is("class") || child.is("record")) {
            var oldAttributes = child.attributes();
            var modifiers = oldAttributes.apply("modifiers")
                    .flatMap(Attribute::asStringList)
                    .orElse(Collections.emptyList());

            var newModifiers = new ArrayList<String>();
            if (modifiers.contains("public")) {
                newModifiers.add("export");
            }

            newModifiers.add("class");
            newModifiers.add("def");

            var name = oldAttributes.apply("name")
                    .flatMap(Attribute::asNode)
                    .flatMap(className -> className.attributes().apply("value"))
                    .flatMap(Attribute::asString)
                    .orElseThrow();

            var definition = new Node("definition")
                    .withString("name", name)
                    .withStringList("modifiers", newModifiers)
                    .withNodeList("params", Collections.emptyList());

            return child.retype("function")
                    .withNode("definition", definition);
        } else {
            return child;
        }
    }
}
