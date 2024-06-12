package magma.compile.lang;

import magma.compile.attribute.NodeListAttribute;
import magma.compile.attribute.StringAttribute;
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
            return root.retype("function").mapAttributes(attributes -> {
                var withNewModifiers = attributes.mapValue("modifiers", StringListAttribute.Factory, oldModifiers -> {
                    var newModifiers = new ArrayList<String>();
                    for (var oldModifier : oldModifiers) {
                        if (oldModifier.equals("public")) {
                            newModifiers.add("export");
                        }
                    }

                    newModifiers.add("class");
                    newModifiers.add("def");
                    return newModifiers;
                });

                return withNewModifiers.mapValue("children", NodeListAttribute.Factory, children -> children.stream()
                        .map(child -> child.mapAttributes(childAttributes -> childAttributes.with("left-indent", new StringAttribute("\t"))))
                        .toList());
            });
        }

        return root;
    }
}