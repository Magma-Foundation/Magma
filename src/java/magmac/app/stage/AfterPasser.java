package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class AfterPasser implements Passer {
    @Override
    public Optional<Tuple2<ParseState, Node>> pass(ParseState state, Node node) {
        if (node.is("root")) {
            List<Node> children = node.findNodeList("children").orElse(new ArrayList<>())
                    .stream()
                    .flatMap(child -> this.expandInherits(child))
                    .toList();

            return Optional.of(new Tuple2<>(state, node.withNodeList("children", children)));
        }

        return Optional.empty();
    }

    private Stream<Node> expandInherits(Node child) {
        Stream<Node> maybeExtends = this.createInherits(child, "extended").stream();
        Stream<Node> maybeImplemented = this.createInherits(child, "implemented").stream();
        return Stream.concat(Stream.of(child), Stream.concat(maybeExtends, maybeImplemented));
    }

    private Optional<Node> createInherits(Node child, String key) {
        return child.findNode(key).map(implemented -> new MapNode("inherits")
                .withString("child", child.findString("name").orElse(""))
                .withString("parent", this.findValue(implemented)));
    }

    private String findValue(Node type) {
        if (type.is("template")) {
            return type.findString("base").orElse("?");
        }

        if (type.is("symbol")) {
            return type.findString("value").orElse("?");
        }

        return "?";
    }
}