package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.compile.MapNode;
import magma.compile.Node;

public class JavaCTransformer implements Transformer {
    @Override
    public Node transform(Node tree, List_<String> namespace) {
        return tree.mapNodeList("children", children -> transformRoot(namespace, children));
    }

    static List_<Node> transformRoot(List_<String> namespace, List_<Node> children) {
        return children.stream()
                .map(child -> transformRootChild(namespace, child))
                .collect(new ListCollector<Node>());
    }

    static Node transformRootChild(List_<String> namespace, Node child) {
        if (child.is("import")) {
            return transformImport(namespace, child);
        } else {
            return child;
        }
    }

    public static Node transformImport(List_<String> currentNamespace, Node node) {
        List_<String> requestedNamespace = node.findNodeList("namespace").orElse(Lists.empty())
                .stream()
                .map(segment -> segment.findString("value").orElse(""))
                .collect(new ListCollector<>());

        List_<Node> path = computeNewNamespace(currentNamespace, requestedNamespace)
                .stream()
                .map(value -> new MapNode().withString("value", value))
                .collect(new ListCollector<>());

        return new MapNode().withNodeList("path", path);
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
}