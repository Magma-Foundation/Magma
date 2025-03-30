#include "TransformAll.h"
Tuple<List_<struct Node>, List_<struct Node>> bucketClassMember(Tuple<List_<struct Node>, List_<struct Node>> tuple, struct Node element}{List_<Node> definitions = tuple.left();
        List_<Node> others = tuple.right();

        if (element.is()) return new Tuple<>(definitions.add(element), others);if (element.is()) {
            Node definition = element.findNode().orElse(new MapNode());
            return new Tuple<>(definitions.add(definition), others);
        }

        return new Tuple<>(definitions, others.add(element));}Result<struct Node, struct CompileError> find(struct Node node, struct String propertyKey}{return node.findNode(propertyKey)
                .<Result<Node, CompileError>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileError( + propertyKey + , new NodeContext(node))));}Result<List_<struct Node>, struct CompileError> findNodeList(struct Node value, struct String propertyKey}{return value.findNodeList(propertyKey)
                .<Result<List_<Node>, CompileError>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileError( + propertyKey + , new NodeContext(value))));}int isFunctionalImport(struct Node child}{if (!child.is()) return false;

        List_<String> namespace = child.findNodeList()
                .orElse(Lists.empty())
                .stream()
                .map(segment -> segment.findString())
                .flatMap(Streams::fromOption)
                .collect(new ListCollector<>());

        return namespace.size() >= 3 && namespace.subList(0, 3).equalsTo(Lists.of(, , ));}int hasTypeParams(struct Node child}{List_<Node> typeParams = child.findNodeList().orElse(Lists.empty());
        return !typeParams.isEmpty();}Result<struct Node, struct CompileError> afterPass(struct State state, struct Node node}{if (node.is() || node.is() || node.is()) {
            return find(node, ).flatMapValue(value -> {
                return findNodeList(value, ).mapValue(children -> {
                    Tuple<List_<Node>, List_<Node>> newChildren = children.stream()
                            .foldWithInitial(new Tuple<>(Lists.empty(), Lists.empty()), TransformAll::bucketClassMember);

                    Node withChildren = node.retype().withNode(, new MapNode()
                            .withNodeList(, newChildren.left()));

                    return new MapNode()
                            .withNode(, withChildren)
                            .withNodeList(, newChildren.right());
                });
            });
        }if (node.is()) {
            return new Ok<>(node.retype());
        }if (node.is()) {
            return new Ok<>(node.mapNodeList(, children -> {
                return children.stream()
                        .filter(child -> !child.is())
                        .collect(new ListCollector<>());
            }));
        }if (node.is()) {
            return findNodeList(node, ).mapValue(requestedNodes -> {
                List_<String> requestedNamespace = requestedNodes.stream()
                        .map(child -> child.findString())
                        .flatMap(Streams::fromOption)
                        .collect(new ListCollector<>());

                List_<String> outputNamespace = Lists.empty();
                int size = state.getNamespace().size();
                if (size == 0) {
                    outputNamespace = outputNamespace.add();
                } else {
                    for (int i = 0; i < size; i++) {
                        outputNamespace = outputNamespace.add();
                    }
                }

                List_<String> newNamespace = requestedNamespace.popFirst()
                        .map(first -> first.left().equals() ? Lists.of().addAll(first.right()) : requestedNamespace)
                        .orElse(requestedNamespace);

                outputNamespace = outputNamespace.addAll(newNamespace);

                List_<Node> path = outputNamespace.stream()
                        .map(segment -> new MapNode().withString(, segment))
                        .collect(new ListCollector<>());

                return node.retype().withNodeList(, path);
            });
        }if (node.is()) {
            Node type = node.findNode().orElse(new MapNode());
            if (type.is()) {
                String value = type.findNodeList()
                        .orElse(Lists.empty())
                        .get(0)
                        .findString()
                        .orElse();

                if (value.equals()) {
                    List_<Node> arguments = type.findNodeList()
                            .orElse(Lists.empty());

                    Node param = arguments.get(0);
                    Node returns = arguments.get(1);

                    return new Ok<>(node.retype()
                            .removeNode()
                            .withNode(, returns)
                            .withNodeList(, Lists.of(param)));
                }
            }
        }if (node.is()) {
            String oldValue = node.findString().orElse();
            if (oldValue.equals()) {
                return new Ok<>(node.withString(, ));
            } else {
                return new Ok<>(node.retype());
            }
        }if (node.is()) {
            return new Ok<>(node.retype());
        }

        return new Ok<>(node);}struct String stringify(struct Node node}{if (node.is()) {
            String caller = node.findNodeList()
                    .orElse(Lists.empty())
                    .stream()
                    .map(element -> element.findString())
                    .flatMap(Streams::fromOption)
                    .collect(new Joiner())
                    .orElse();

            return node.findNodeList().orElse(new JavaList<>())
                    .stream()
                    .map(this::stringify)
                    .collect(new Joiner())
                    .map(arguments -> caller +  + arguments)
                    .orElse(caller);
        }

        return node.findString().orElse();}Result<struct Node, struct CompileError> beforePass(struct State state, struct Node node}{if (!node.is()) return new Ok<>(node);

        Node content = node.findNode().orElse(new MapNode());
        List_<Node> children = content.findNodeList().orElse(Lists.empty());

        List_<Node> newChildren = children.stream()
                .filter(child -> !isFunctionalImport(child) && !child.is() && !hasTypeParams(child))
                .collect(new ListCollector<>());

        Node withChildren = content.withNodeList(, newChildren);
        return new Ok<>(node.withNode(, withChildren));}