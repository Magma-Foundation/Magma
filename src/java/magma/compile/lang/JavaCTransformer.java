package magma.compile.lang;

import jvm.collect.stream.Streams;
import jvm.collect.list.Lists;
import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;

public class JavaCTransformer implements Transformer {
    static List_<Node> transformRoot(List_<String> namespace, List_<Node> children) {
        return children.stream()
                .map(child -> transformRootChild(namespace, child))
                .flatMap(Streams::fromOption)
                .collect(new ListCollector<Node>());
    }

    static Option<Node> transformRootChild(List_<String> namespace, Node child) {
        if (child.is("package")) return new None<>();

        if (child.is("import")) {
            List_<String> requestedNamespace = child.findNodeList("namespace").orElse(Lists.empty())
                    .stream()
                    .map(segment -> segment.findString("value").orElse(""))
                    .collect(new ListCollector<>());

            if (requestedNamespace.size() >= 3 && requestedNamespace.subList(0, 3).equalsTo(Lists.of("java", "util", "function")))
                return new None<>();

            List_<Node> path = computeNewNamespace(namespace, requestedNamespace)
                    .stream()
                    .map(value -> new MapNode().withString("value", value))
                    .collect(new ListCollector<>());

            return new Some<>(child.retype("include").withNodeList("path", path));
        }

        if (child.is("class")) return new Some<>(child.retype("struct"));
        if (child.is("interface")) return new Some<>(child.retype("struct"));
        if (child.is("record")) return new Some<>(child.retype("struct"));
        return new Some<>(child);
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
        return tree.mapNodeList("children", children -> transformRoot(namespace, children));
    }
}