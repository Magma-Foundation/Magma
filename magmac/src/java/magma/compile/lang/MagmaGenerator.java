package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

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

        if (typeOptional.isEmpty()) return definition;
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

    private static Function<Node, Node> getNodeNodeFunction() {
        return child -> {
            if (!child.is("definition")) return child;

            var paramsOptional = child.attributes()
                    .apply("params")
                    .flatMap(Attribute::asNodeList);

            if (paramsOptional.isEmpty()) return child;

            var params = paramsOptional.get();
            return child
                    .remove("params")
                    .mapNode("type", returnType -> new Node("function-type")
                            .withNodeList("params", params)
                            .withNode("returns", returnType));
        };
    }

    @Override
    protected Tuple<Node, Integer> postVisit(Node node, int depth) {
        var newNode = getNodeIntegerTuple(node, depth);
        if (newNode != null) return newNode;

        var definition = getIntegerTuple(node, depth);
        if (definition != null) return definition;

        var definition1 = getTuple(node, depth);
        if (definition1 != null) return definition1;

        var removed = getNodeIntegerTuple1(node, depth);
        if (removed != null) return removed;

        var node2 = getNodeIntegerTuple2(node, depth);
        if (node2 != null) return node2;

        var node3 = getNodeIntegerTuple3(node, depth);
        if (node3 != null) return node3;

        var struct = generateFromInterface(node, depth);
        if (struct.isPresent()) return struct.get();

        var node1 = generateFromFunction(node, depth);
        return node1.orElseGet(() -> new Tuple<>(node, depth));

    }

    private static Optional<Tuple<Node, Integer>> generateFromInterface(Node node, int depth) {
        if (!node.is("interface")) {
            return Optional.empty();
        }

        var struct = node.retype("struct").mapAttributes(attribute -> {
            var withModifiers = attribute.mapValue("modifiers", StringListAttribute.Factory, list -> {
                var newList = new ArrayList<String>();
                if (list.contains("public")) {
                    newList.add("export");
                }
                return newList;
            });

            return withModifiers.mapValue("child", NodeAttribute.Factory, interfaceChild -> interfaceChild.mapNodes("children", children -> children.stream()
                    .map(getNodeNodeFunction())
                    .toList()));
        });

        return Optional.of(new Tuple<>(struct, depth));
    }

    private static Tuple<Node, Integer> getNodeIntegerTuple3(Node node, int depth) {
        if (node.is("record")) {
            return new Tuple<>(node.retype("function"), depth);
        }
        return null;
    }

    private static Tuple<Node, Integer> getNodeIntegerTuple2(Node node, int depth) {
        if (node.is("constructor")) {
            return new Tuple<>(node.retype("invocation"), depth);
        }
        return null;
    }

    private static Tuple<Node, Integer> getNodeIntegerTuple1(Node node, int depth) {
        if (node.is("definition")) {
            var removed = removeImplicitType(node);

            var modifiers = removed.attributes()
                    .apply("modifiers")
                    .flatMap(Attribute::asStringList);

            if (modifiers.isEmpty() || modifiers.get().isEmpty()) {
                return new Tuple<>(removed.remove("modifiers"), depth);
            } else {
                return new Tuple<>(removed, depth);
            }
        }
        return null;
    }

    private static Tuple<Node, Integer> getTuple(Node node, int depth) {
        if (node.is("declaration")) {
            var definition = node.mapNode("definition:scope", scope -> {
                return scope.mapNode("definition", MagmaGenerator::attachModifiers);
            });

            return new Tuple<>(definition, depth);
        }
        return null;
    }

    private static Tuple<Node, Integer> getIntegerTuple(Node node, int depth) {
        if (node.is("function")) {
            if (!node.has("child")) {
                var definition = node.attributes()
                        .apply("definition")
                        .flatMap(Attribute::asNode)
                        .orElseThrow();

                return new Tuple<>(definition, depth);
            }
        }
        return null;
    }

    private static Tuple<Node, Integer> getNodeIntegerTuple(Node node, int depth) {
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
                            .withNodeList("params", Collections.singletonList(first))
                            .withNode("returns", second);

                    return new Tuple<>(newNode, depth);
                }
            }
        }
        return null;
    }

    private static Optional<Tuple<Node, Integer>> generateFromFunction(Node node, int depth) {
        if (!node.is("function")) return Optional.empty();

        var node1 = node.mapAttributes(attributes -> attributes.mapValue("definition", NodeAttribute.Factory, definition -> {
            if (!definition.attributes().has("name")) {
                return definition;
            }

            return definition.mapAttributes(attributes1 -> attributes1.mapValue("modifiers", StringListAttribute.Factory, list -> {
                if (list.contains("class")) return list;

                var copy = new ArrayList<String>();
                if (list.contains("public")) {
                    copy.add("public");
                }

                copy.add("def");
                return copy;
            }));
        }));

        return Optional.of(new Tuple<>(node1, depth));
    }
}
