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
        var typeOptional = definition.attributes()
                .apply("type")
                .flatMap(Attribute::asNode);

        if(typeOptional.isEmpty()) return definition;
        var type = typeOptional.get();

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
        if (node.is("generic-type")) {
            var parent = node.attributes()
                    .apply("parent")
                    .flatMap(Attribute::asNode)
                    .orElseThrow();

            if (parent.is("symbol")) {
                var value = parent.attributes()
                        .apply("value")
                        .flatMap(Attribute::asString)
                        .orElseThrow();

                if (value.equals("Function")) {
                    var children = node.attributes()
                            .apply("children")
                            .flatMap(Attribute::asNodeList)
                            .orElseThrow();

                    var first = children.get(0);
                    var second = children.get(1);

                    var newNode = new Node("function-type")
                            .withNode("from", first)
                            .withNode("to", second);

                    return new Tuple<>(newNode, depth);
                }
            }
        }

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
            var removed = removeImplicitType(node);

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
            var struct = node.retype("struct").mapAttributes(attribute -> {
                return attribute.mapValue("modifiers", StringListAttribute.Factory, list -> {
                    var newList = new ArrayList<String>();
                    if (list.contains("public")) {
                        newList.add("export");
                    }
                    return newList;
                });
            });

            return new Tuple<>(struct, depth);
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

        return new Tuple<>(node, depth);
    }
}
