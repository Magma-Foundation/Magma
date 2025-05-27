package magmac.app.lang;

import magmac.api.Tuple2;
import magmac.api.collect.ListCollector;
import magmac.api.iter.Iter;
import magmac.api.iter.Iters;
import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.stage.Passer;
import magmac.app.stage.parse.ParseState;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AfterPasser implements Passer {
    private static Optional<Node> createInherits(Node child, String key) {
        return child.findNode(key).map(implemented -> new MapNode("inherits")
                .withString("child", child.findString("name").orElse(""))
                .withString("parent", AfterPasser.findValue(implemented)));
    }

    private static String findValue(Node type) {
        if (type.is("template")) {
            return type.findString("base").orElse("?");
        }

        if (type.is("symbol")) {
            return type.findString("value").orElse("?");
        }

        return "?";
    }

    private static Iter<Node> replaceRootChild(Node child) {
        Iter<Node> maybeExtends = Iters.fromOption(AfterPasser.createInherits(child, "extended"));
        Iter<Node> maybeImplemented = Iters.fromOption(AfterPasser.createInherits(child, "implemented"));
        return Iters.fromValues(child).concat(maybeExtends).concat(maybeImplemented);
    }

    @Override
    public Optional<Tuple2<ParseState, Node>> pass(ParseState state, Node node) {
        if (node.is("root")) {
            List<Node> children = AfterPasser.replaceRootChildren(node);
            return Optional.of(new Tuple2<>(state, node.withNodeList("children", new InlineNodeList(children))));
        }

        if (node.is("import")) {
            String child = node.findNodeList("segments")
                    .orElse(InlineNodeList.empty())
                    .last()
                    .findString("value")
                    .orElse("");

            Node dependency = new MapNode("dependency")
                    .withString("parent", state.findLocation().name())
                    .withString("child", child);

            return Optional.of(new Tuple2<ParseState, Node>(state, dependency));
        }

        return Optional.empty();
    }

    private static List<Node> replaceRootChildren(Node node) {
        return node.findNodeList("children")
                .orElse(InlineNodeList.empty())
                .iter()
                .flatMap(child -> AfterPasser.replaceRootChild(child))
                .collect(new ListCollector<>());
    }
}