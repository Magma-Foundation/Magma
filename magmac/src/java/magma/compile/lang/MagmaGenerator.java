package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
                    .map(MagmaGenerator::migrateDefinition)
                    .toList()));
        });

        return Optional.of(new Tuple<>(struct, depth));
    }

    private static Node migrateDefinition(Node child) {
        if (!child.is("definition")) return child;

        var paramsOptional = child.attributes()
                .apply("params")
                .flatMap(Attribute::asNodeList);

        if (paramsOptional.isEmpty()) return child;

        var params = paramsOptional.get()
                .stream()
                .map(Node::attributes)
                .map(attributes -> attributes.apply("type"))
                .flatMap(Optional::stream)
                .map(Attribute::asNode)
                .flatMap(Optional::stream)
                .toList();

        return child.remove("params")
                .mapOrSetStringList("modifiers", list -> add(list, "const"), () -> Collections.singletonList("const"))
                .mapNode("type", returnType -> buildFunctionType(returnType, params));
    }

    private static <T> List<T> add(List<T> list, T element) {
        var copy = new ArrayList<>(list);
        copy.add(element);
        return copy;
    }

    private static Node buildFunctionType(Node returnType, List<Node> params) {
        var returns = new Node("function-type")
                .withNode("returns", returnType);

        if (params.isEmpty()) {
            return returns;
        } else {
            return returns.withNodeList("params", params);
        }
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

                    var newNode = buildFunctionType(second, Collections.singletonList(first));
                    return new Tuple<>(newNode, depth);
                }
            }
        }
        return null;
    }

    private static Optional<Tuple<Node, Integer>> generateFromFunction(Node node, int depth) {
        if (!node.is("function")) return Optional.empty();
        var withDefinition = node.mapNode("definition", MagmaGenerator::generateFunctionDefinition);
        var node1 = generateImplements(withDefinition);
        return Optional.of(new Tuple<>(node1, depth));
    }

    private static Node generateImplements(Node withDefinition) {
        var maybeInterface = withDefinition.attributes()
                .apply("implements")
                .flatMap(Attribute::asNode);

        return maybeInterface.map(interfaceType -> {
            return withDefinition.mapNode("child", child -> {
                return child.mapNodes("children", list -> {
                    var element = new Node("implements").withNode("type", fromNameToInstanceType(interfaceType));
                    return add(list, element);
                });
            });
        }).orElse(withDefinition);
    }

    private static Node fromNameToInstanceType(Node interfaceType) {
        if (interfaceType.is("symbol-name")) {
            return interfaceType.retype("symbol");
        }

        if (interfaceType.is("generic-name")) {
            var parent = interfaceType.attributes()
                    .apply("parent")
                    .flatMap(Attribute::asString)
                    .orElseThrow();

            var child = interfaceType.attributes()
                    .apply("value")
                    .flatMap(Attribute::asString)
                    .orElseThrow();

            return new Node("generic-type")
                    .withNode("parent", new Node("symbol").withString("value", parent))
                    .withNodeList("children", List.of(new Node("symbol").withString("value", child)));
        }

        return interfaceType;
    }

    private static Node generateFunctionDefinition(Node definition) {
        if (definition.has("name")) {
            return definition.mapOrSetStringList("modifiers", MagmaGenerator::generateFunctionModifiers, Collections::emptyList);
        }

        return definition;
    }

    private static List<String> generateFunctionModifiers(List<String> list) {
        if (list.contains("class")) return list;

        var copy = new ArrayList<String>();
        if (list.contains("public")) {
            copy.add("public");
        }

        copy.add("def");
        return copy;
    }

    @Override
    protected Tuple<Node, Integer> postVisit(Node node, int depth) {
        if (node.is("block")) {
            return new Tuple<>(node.mapNodes("children", list -> {
                return list.stream()
                        .filter(child -> !child.is("empty"))
                        .toList();
            }), depth);
        }

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
}
