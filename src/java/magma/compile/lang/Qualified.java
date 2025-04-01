package magma.compile.lang;

import jvm.collect.list.Lists;
import jvm.collect.stream.Streams;
import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.compile.MapNode;
import magma.compile.Node;

public class Qualified {
    public static Node to(List_<String> list) {
        List_<Node> resolved = list.stream()
                .map(segment -> new MapNode().withString("value", segment))
                .collect(new ListCollector<Node>());

        return new MapNode("qualified").withNodeList("segments", resolved);
    }

    public static List_<String> from(Node node) {
        return node.findNodeList("segments")
                .orElse(Lists.empty())
                .stream()
                .map(segment -> segment.findString("value"))
                .flatMap(Streams::fromOption)
                .collect(new ListCollector<String>());
    }
}