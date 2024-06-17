package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;

public class MagmaGenerator extends Generator {
    private static Node attachModifiers(Node definition) {
        var oldModifiers = definition.attributes()
                .apply("modifiers")
                .flatMap(Attribute::asStringList)
                .orElse(new ArrayList<>());

        var copy = new ArrayList<String>();
        if (oldModifiers.contains("final")) {
            copy.add("const");
        } else {
            copy.add("let");
        }

        return definition.withStringList("modifiers", copy);
    }

    private static Node removeImplicitType(Node definition) {
        var type = definition.attributes()
                .apply("type")
                .flatMap(Attribute::asNode)
                .orElseThrow();

        if (type.is("symbol")) {
            var value = type.attributes()
                    .apply("value")
                    .flatMap(Attribute::asString)
                    .orElseThrow();

            if (value.equals("var")) {
                return definition.remove("type");
            } else {
                return definition;
            }
        } else {
            return definition;
        }
    }

    @Override
    protected Tuple<Node, Integer> postVisit(Node node, int depth) {
        if (node.is("function")) {
            if (!node.has("child")) {
                var definition = node.attributes()
                        .apply("definition")
                        .flatMap(Attribute::asNode)
                        .orElseThrow();

                return new Tuple<>(definition, depth);
            }
        }

        if (node.is("definition")) {
            var modifiers = node.attributes()
                    .apply("modifiers")
                    .flatMap(Attribute::asStringList);

            if (modifiers.isEmpty() || modifiers.get().isEmpty()) {
                return new Tuple<>(node.remove("modifiers"), depth);
            } else {
                return new Tuple<>(node, depth);
            }
        }

        if (node.is("constructor")) {
            return new Tuple<>(node.retype("invocation"), depth);
        }

        if (node.is("record")) {
            return new Tuple<>(node.retype("function"), depth);
        }

        if (node.is("interface")) {
            return new Tuple<>(node.retype("trait"), depth);
        }

        if (node.is("function")) {
            var node1 = node.mapAttributes(attributes -> {
                return attributes.mapValue("definition", NodeAttribute.Factory, definition -> {
                    if (!definition.attributes().has("name")) {
                        return definition;
                    }

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

            });
            return new Tuple<>(node1, depth);
        }

        if (node.is("declaration")) {
            var withModifiers = node.mapNode("definition:scope",
                    scope -> scope.mapNode("definition",
                            definition -> removeImplicitType(attachModifiers(definition))));

            return new Tuple<>(withModifiers, depth);
        }

        return new Tuple<>(node, depth);
    }
}
