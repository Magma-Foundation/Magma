package magma.compile.lang;

import magma.compile.attribute.NodeListAttribute;
import magma.compile.attribute.StringAttribute;
import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        if (root.is("root")) {
            return root.mapAttributes(attributes -> {
                return attributes.mapValue("children", NodeListAttribute.Factory, list -> {
                    return list.stream()
                            .filter(element -> !element.is("package") && !element.is("whitespace"))
                            .toList();
                });
            });
        }

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

        if (root.is("method")) {
            return root.retype("function").mapAttributes(attributes -> attributes
                    .mapValue("modifiers", StringListAttribute.Factory, oldModifiers -> {
                        var newModifiers = new ArrayList<String>();
                        if(oldModifiers.contains("public")) newModifiers.add("public");
                        newModifiers.add("def");
                        return newModifiers;
                    })
                    .with("right-padding", new StringAttribute("\t"))
                    .with("children", new NodeListAttribute(Collections.emptyList())));
        }

        if (root.is("declaration")) {
            return root.mapAttributes(attributes -> {
                return attributes.mapValue("modifiers", StringListAttribute.Factory, modifiers -> {
                    var newModifiers = new ArrayList<String>();

                    if (modifiers.contains("public")) newModifiers.add("public");
                    if (modifiers.contains("final")) {
                        newModifiers.add("const");
                    } else newModifiers.add("let");

                    return newModifiers;
                });
            });
        }

        return root;
    }
}