package magma.compile.lang;

import jvm.collect.list.Lists;
import jvm.collect.stream.Streams;
import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.collect.stream.Joiner;
import magma.collect.stream.Stream;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.option.Tuple;

public class JavaCTransformer implements Transformer {
    static List_<Node> transformRoot(List_<String> namespace, List_<Node> children) {
        return children.stream()
                .flatMap(child -> transformRootChild(namespace, child))
                .collect(new ListCollector<>());
    }

    static Stream<Node> transformRootChild(List_<String> namespace, Node node) {
        if (node.is("package") || node.is("whitespace")) return Streams.empty();

        if (node.is("import")) {
            List_<String> requestedNamespace = node.findNodeList("namespace").orElse(Lists.empty())
                    .stream()
                    .map(segment -> segment.findString("value").orElse(""))
                    .collect(new ListCollector<>());

            if (requestedNamespace.size() >= 3 && requestedNamespace.subList(0, 3).equalsTo(Lists.of("java", "util", "function")))
                return Streams.empty();

            List_<Node> path = computeNewNamespace(namespace, requestedNamespace)
                    .stream()
                    .map(value -> new MapNode().withString("value", value))
                    .collect(new ListCollector<>());

            return Streams.of(node.retype("include").withNodeList("path", path));
        }

        if (node.is("class") || node.is("record") || node.is("interface")) {
            List_<Node> children = node.findNodeList("children").orElse(Lists.empty());

            Tuple<List_<Node>, List_<Node>> tuple = children.stream()
                    .map(JavaCTransformer::transformClassMember)
                    .foldWithInitial(new Tuple<>(Lists.empty(), Lists.empty()), JavaCTransformer::foldNode);

            Node newStruct = node.retype("struct").withNodeList("children", tuple.left());
            return Lists.<Node>empty().add(newStruct).addAll(tuple.right()).stream();
        }

        return Streams.of(node);
    }

    private static Node transformClassMember(Node node) {
        if (node.is("initialization")) {
            return node.findNode("definition")
                    .orElse(new MapNode())
                    .mapNode("type", JavaCTransformer::transformType);
        }

        if (node.is("definition")) {
            return node.mapNode("type", JavaCTransformer::transformType);
        }

        if (node.is("method")) {
            return node.findNodeList("children").map(children -> {
                return node.retype("function");
            }).orElseGet(() -> {
                Node definition = node.findNode("definition").orElse(new MapNode());
                String name = definition.findString("name").orElse("");
                Node oldType = definition.findNode("type").orElse(new MapNode());
                Node returns = transformType(oldType);

                List_<Node> paramTypes = node.findNodeList("params")
                        .orElse(Lists.empty())
                        .stream()
                        .map(child -> child.findNode("type"))
                        .flatMap(Streams::fromOption)
                        .map(JavaCTransformer::transformType)
                        .collect(new ListCollector<>());

                return new MapNode("functional-definition")
                        .withString("name", name)
                        .withNode("returns", returns)
                        .withNodeList("params", paramTypes);
            });
        }

        return node;
    }

    private static Node transformType(Node oldType) {
        if (oldType.is("generic")) {
            return new MapNode("symbol-type").withString("value", stringify(oldType));
        }
        return oldType;
    }

    private static String stringify(Node type) {
        if (type.is("generic")) {
            String base = type.findString("base").orElse("");
            return type.findNodeList("arguments")
                    .orElse(Lists.empty())
                    .stream()
                    .map(JavaCTransformer::stringify)
                    .collect(new Joiner("_"))
                    .map(inner -> base + "_" + inner)
                    .orElse(base);
        } else {
            return type.findString("value").orElse("");
        }
    }

    private static Tuple<List_<Node>, List_<Node>> foldNode(Tuple<List_<Node>, List_<Node>> current, Node classMember) {
        if (classMember.is("whitespace")) return current;

        if (classMember.is("definition") || classMember.is("functional-definition"))
            return new Tuple<>(current.left().add(classMember), current.right());

        return new Tuple<>(current.left(), current.right().add(classMember));
    }

    private static List_<String> computeNewNamespace(List_<String> thisNamespace, List_<String> requestedNamespace) {
        List_<String> copy = Lists.empty();
        for (int i = 0; i < thisNamespace.size(); i++) {
            copy = copy.add("..");
        }

        copy = copy.addAll(maybeReplacePlatformImport(requestedNamespace));
        return copy;
    }

    private static List_<String> maybeReplacePlatformImport(List_<String> namespace) {
        if (!isNativePlatformImport(namespace)) return namespace;

        return Lists.<String>empty()
                .add("windows")
                .addAll(namespace.subList(1, namespace.size()));
    }

    private static boolean isNativePlatformImport(List_<String> namespace) {
        return namespace.findFirst()
                .filter(value -> value.equals("jvm"))
                .isPresent();
    }

    @Override
    public Node transform(Node tree, List_<String> namespace) {
        Node withNodes = tree.streamNodes()
                .foldWithInitial(tree, (node, tuple) -> mapNodes(namespace, node, tuple));

        return withNodes.streamNodeLists().foldWithInitial(withNodes,
                (node, tuple) -> mapNodeList(namespace, node, tuple));
    }

    private Node mapNodes(List_<String> namespace, Node node, Tuple<String, Node> tuple) {
        Node newChild = transform(tuple.right(), namespace);
        return node.withNode(tuple.left(), newChild);
    }

    private Node mapNodeList(List_<String> namespace, Node node, Tuple<String, List_<Node>> tuple) {
        List_<Node> children = tuple.right()
                .stream()
                .map(child -> transform(child, namespace))
                .collect(new ListCollector<>());

        return node.withNodeList(tuple.left(), children);
    }
}