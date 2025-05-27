package magmac.app.lang;

import magmac.api.Tuple2;
import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.stage.Passer;
import magmac.app.stage.parse.ParseState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

    @Override
    public Optional<Tuple2<ParseState, Node>> pass(ParseState state, Node node) {
        if (node.is("root")) {
            List<Node> children = node.findNodeList("children").map(list -> list.unwrap()).orElse(new ArrayList<>())
                    .stream()
                    .flatMap(child -> AfterPasser.expandInherits(child))
                    .toList();

            return Optional.of(new Tuple2<>(state, node.withNodeList("children", new InlineNodeList(children))));
        }

        if (node.is("import")) {
            String child = node.findNodeList("segments").map(list -> list.unwrap())
                    .orElse(Collections.emptyList())
                    .getLast()
                    .findString("value")
                    .orElse("");

            Node dependency = new MapNode("dependency")
                    .withString("parent", state.findLocation().name())
                    .withString("child", child);

            return Optional.of(new Tuple2<ParseState, Node>(state, dependency));
        }

        return Optional.empty();
    }

    private static Stream<Node> expandInherits(Node child) {
        Stream<Node> maybeExtends = AfterPasser.createInherits(child, "extended").stream();
        Stream<Node> maybeImplemented = AfterPasser.createInherits(child, "implemented").stream();
        return Stream.concat(Stream.of(child), Stream.concat(maybeExtends, maybeImplemented));
    }
}