package magma.compile.lang;

import magma.compile.attribute.Attribute;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;

public class JavaToMagmaGenerator {
    public static Node generate(Node node) {
        var withChildren = node.mapAttributes(attributes -> attributes.mapValues(attribute -> {
            var list = attribute.asNodeList();
            if (list.isPresent()) {
                return new NodeListAttribute(list.get().stream()
                        .map(JavaToMagmaGenerator::generate)
                        .toList());
            } else {
                return attribute;
            }
        }));

        return postVisit(withChildren);
    }

    private static Node postVisit(Node root) {
        if (root.is("class")) {
            var oldAttributes = root.attributes();
            var oldModifiers = oldAttributes.apply("modifiers")
                    .flatMap(Attribute::asStringList)
                    .orElseThrow();

            var newModifiers = new ArrayList<String>();
            for (var oldModifier : oldModifiers) {
                if (oldModifier.equals("public")) {
                    newModifiers.add("export");
                }
            }

            newModifiers.add("class");
            newModifiers.add("def");

            var newAttributes = oldAttributes.with("modifiers", new StringListAttribute(newModifiers));
            return root.retype("function").withAttributes(newAttributes);
        }

        if (root.is("import")) return root;
        return root;
    }
}