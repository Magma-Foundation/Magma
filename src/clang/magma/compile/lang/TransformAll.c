#include "TransformAll.h"
magma.option.Tuple<magma.collect.list.List_<magma.compile.Node>, magma.collect.list.List_<magma.compile.Node>> bucketClassMember(magma.option.Tuple<magma.collect.list.List_<magma.compile.Node>, magma.collect.list.List_<magma.compile.Node>> tuple, magma.compile.Node element){List_<Node> definitions = tuple.left();
        List_<Node> others = tuple.right();if (element.is("definition")) return new Tuple<>(definitions.add(element), others);if (element.is("initialization")) {
            Node definition = element.findNode("definition").orElse(new MapNode());
            return new Tuple<>(definitions.add(definition), others);
        }return (definitions, others.add(element));
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> find(magma.compile.Node node, String propertyKey){return node.findNode(propertyKey).map(Ok.new).orElseGet(__lambda0__);
}
magma.result.Result<magma.collect.list.List_<magma.compile.Node>, magma.compile.CompileError> findNodeList(magma.compile.Node value, String propertyKey){return value.findNodeList(propertyKey).map(Ok.new).orElseGet(__lambda1__);
}
int isFunctionalImport(magma.compile.Node child){if (!child.is("import")) return false;

        List_<String> namespace = child.findNodeList("namespace")
                .orElse(Lists.empty())
                .stream()
                .map(segment -> segment.findString("value"))
                .flatMap(Streams::fromOption)
                .collect(new ListCollector<>());

        return namespace.size() >= 3 && namespace.subList(0, 3).equalsTo(Lists.of("java", "util", "function"));
}
int hasTypeParams(magma.compile.Node child){List_<Node> typeParams = child.findNodeList("type-params").orElse(Lists.empty());return !typeParams.isEmpty();
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> beforePass(magma.compile.transform.State state, magma.compile.Node node){if (node.is("root")) {
            Node content = node.findNode("content").orElse(new MapNode());
            List_<Node> children = content.findNodeList("children").orElse(Lists.empty());
            List_<Node> newChildren = children.stream()
                    .filter(child -> !isFunctionalImport(child) && !child.is("package"))
                    .collect(new ListCollector<>());

            Node withChildren = content.withNodeList("children", newChildren);
            Node withContent = node.withNode("content", withChildren);
            return new Ok<>(new Tuple<>(state, withContent));
        }if (!node.is("definition")) return new Ok<>(new Tuple<>(state, node));

        Node type = node.findNode("type").orElse(new MapNode());if (!type.is("generic")) return new Ok<>(new Tuple<>(state, node));

        List_<String> qualifiedName = StringLists.fromQualified(type.findNode("base")
                .orElse(new MapNode()));

        List_<Node> arguments = type.findNodeList("arguments").orElseGet(Lists::empty);if (qualifiedName.equalsTo(Lists.of("java", "util", "function", "BiFunction"))) {
            Node param0 = arguments.get(0);
            Node param1 = arguments.get(1);
            Node returns = arguments.get(2);

            Node definition = node.retype("functional-definition")
                    .removeNode("type")
                    .withNode("return", returns)
                    .withNodeList("params", Lists.of(param0, param1));

            return new Ok<>(new Tuple<>(state, definition));
        }if (qualifiedName.equalsTo(Lists.of("java", "util", "function", "Function"))) {
            Node param = arguments.get(0);
            Node returns = arguments.get(1);

            Node definition = node.retype("functional-definition")
                    .removeNode("type")
                    .withNode("return", returns)
                    .withNodeList("params", Lists.of(param));

            return new Ok<>(new Tuple<>(state, definition));
        }if (qualifiedName.equalsTo(Lists.of("java", "util", "function", "Predicate"))) {
            Node param = arguments.get(0);

            Node returns = StringLists.toQualified(Lists.of("int"));
            Node definition = node.retype("functional-definition")
                    .removeNode("type")
                    .withNode("return", returns)
                    .withNodeList("params", Lists.of(param));

            return new Ok<>(new Tuple<>(state, definition));
        }if (qualifiedName.equalsTo(Lists.of("java", "util", "function", "Consumer"))) {
            Node paramType = arguments.get(0);

            Node qualified = StringLists.toQualified(Lists.of("void"));
            Node definition = node.retype("functional-definition")
                    .removeNode("type")
                    .withNode("return", qualified)
                    .withNodeList("params", Lists.of(paramType));

            return new Ok<>(new Tuple<>(state, definition));
        }if (qualifiedName.equalsTo(Lists.of("java", "util", "function", "Supplier"))) {
            Node returns = arguments.get(0);

            Node definition = node.retype("functional-definition")
                    .removeNode("type")
                    .withNode("return", returns);

            return new Ok<>(new Tuple<>(state, definition));
        }return ((state, node));
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> afterPass(magma.compile.transform.State state, magma.compile.Node node){if (node.is("interface") || node.is("record") || node.is("class")) {
            return find(node, "content").flatMapValue(value -> {
                return findNodeList(value, "children").mapValue(children -> {
                    Tuple<List_<Node>, List_<Node>> newChildren = children.stream()
                            .foldWithInitial(new Tuple<>(Lists.empty(), Lists.empty()), TransformAll::bucketClassMember);

                    Node withChildren = node.retype("struct").withNode("content", new MapNode("block")
                            .withNodeList("children", newChildren.left()));

                    return new Tuple<>(state, new MapNode("group")
                            .withNode("child", withChildren)
                            .withNodeList("functions", newChildren.right()));
                });
            });
        }if (node.is("method")) {
            return new Ok<>(new Tuple<>(state, node.retype("function")));
        }if (node.is("block")) {
            return new Ok<>(new Tuple<>(state, node.mapNodeList("children", children -> {
                return children.stream()
                        .filter(child -> !child.is("whitespace"))
                        .collect(new ListCollector<>());
            })));
        }if (node.is("import")) {
            return findNodeList(node, "namespace").mapValue(requestedNodes -> {
                List_<String> requestedNamespace = requestedNodes.stream()
                        .map(child -> child.findString("value"))
                        .flatMap(Streams::fromOption)
                        .collect(new ListCollector<>());

                List_<String> outputNamespace = Lists.empty();
                int size = state.namespace().size();
                if (size == 0) {
                    outputNamespace = outputNamespace.add(".");
                } else {
                    for (int i = 0; i < size; i++) {
                        outputNamespace = outputNamespace.add("..");
                    }
                }

                List_<String> newNamespace = requestedNamespace.popFirst()
                        .map(first -> first.left().equals("jvm") ? Lists.of("windows").addAll(first.right()) : requestedNamespace)
                        .orElse(requestedNamespace);

                outputNamespace = outputNamespace.addAll(newNamespace);

                List_<Node> path = outputNamespace.stream()
                        .map(segment -> new MapNode().withString("value", segment))
                        .collect(new ListCollector<>());

                return new Tuple<>(state, node.retype("include").withNodeList("path", path));
            });
        }if (node.is("array")) {
            return new Ok<>(new Tuple<>(state, node.retype("ref")));
        }if (node.is("lambda")) {
            Node child = node.findNode("child")
                    .orElse(new MapNode());

            String generatedName = "__lambda" + counter + "__";
            counter++;

            Node definition = new MapNode("definition")
                    .withString("name", generatedName)
                    .withNode("type", StringLists.toQualified(Lists.of("auto")));

            Node function = new MapNode("function")
                    .withNode("definition", definition);

            return new Ok<>(new Tuple<>(state, new MapNode("group")
                    .withNode("child", new MapNode("symbol-value").withString("value", generatedName))
                    .withNodeList("functions", Lists.of(function))));
        }if (node.is("method-access")) {
            return new Ok<>(new Tuple<>(state, node.retype("data-access")));
        }if (node.is("construction")) {
            List_<String> list = StringLists.fromQualified(node.findNode("type")
                    .orElse(new MapNode()));

            Node caller = new MapNode("symbol-value").withString("value", list.findLast().orElse(""));
            return new Ok<>(new Tuple<>(state, node.retype("invocation").withNode("caller", caller)));
        }return ((state, node));
}
auto __lambda0__();
auto __lambda1__();

