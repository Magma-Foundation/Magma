#include "TransformAll.h"
int isFunctionalImport(Node child){if (!child.is("import")) return false;

        List_<String> namespace = child.findNodeList("namespace")
                .orElse(Lists.empty())
                .stream()
                .map(segment -> segment.findString("value"))
                .flatMap(Streams::fromOption)
                .collect(new ListCollector<>());

        return namespace.size() >= 3 && namespace.subList(0, 3).equalsTo(Lists.of("java", "util", "function"));
}
Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node){if (node.is("root")) {
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

        List_<String> qualifiedName = Qualified.from(type.findNode("base")
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

            Node returns = Qualified.to(Lists.of("int"));
            Node definition = node.retype("functional-definition")
                    .removeNode("type")
                    .withNode("return", returns)
                    .withNodeList("params", Lists.of(param));

            return new Ok<>(new Tuple<>(state, definition));
        }if (qualifiedName.equalsTo(Lists.of("java", "util", "function", "Consumer"))) {
            Node paramType = arguments.get(0);

            Node qualified = Qualified.to(Lists.of("void"));
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
Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node){if (node.is("method")) {
            return new Ok<>(new Tuple<>(state, node.retype("function")));
        }if (node.is("block")) {
            return new Ok<>(new Tuple<>(state, node.mapNodeList("children", children -> {
                return children.stream()
                        .filter(child -> !child.is("whitespace"))
                        .collect(new ListCollector<>());
            })));
        }if (node.is("import")) {
            return Transformers.findNodeList(node, "namespace").mapValue(requestedNodes -> {
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
            Option<Node> maybeChild = node.findNode("child");
            Node propertyValue;
            if (maybeChild.isPresent()) {
                Node child = maybeChild.orElse(new MapNode());
                propertyValue = new MapNode("block")
                        .withNodeList("children", Lists.of(new MapNode("return")
                                .withNode("child", child)));
            } else {
                propertyValue = node.findNode("content").orElse(new MapNode());
            }

            String generatedName = "__lambda" + counter + "__";
            counter++;

            Node definition = new MapNode("definition")
                    .withString("name", generatedName)
                    .withNode("type", Qualified.to(Lists.of("int")));

            Node function = new MapNode("function")
                    .withNode("definition", definition)
                    .withNode("content", propertyValue);

            return new Ok<>(new Tuple<>(state, new MapNode("group")
                    .withNode("child", new MapNode("symbol-value").withString("value", generatedName))
                    .withNodeList("functions", Lists.of(function))));
        }if (node.is("method-access")) {
            return new Ok<>(new Tuple<>(state, node.retype("data-access")));
        }if (node.is("construction")) {
            List_<String> list = Qualified.from(node.findNode("type")
                    .orElse(new MapNode()));

            Node caller = new MapNode("symbol-value").withString("value", list.findLast().orElse(""));
            return new Ok<>(new Tuple<>(state, node.retype("invocation").withNode("caller", caller)));
        }return ((state, node));
}
