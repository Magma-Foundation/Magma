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
                    .flatMap(child -> {
                        return child.findNode("implemented")
                                .map(implemented -> {
                                    Stream<Node> child1 = Stream.of(child, new MapNode("inherits")
                                            .withString("child", child.findString("name").orElse(""))
                                            .withString("parent", this.findValue(implemented)));
                                    return child1;
                                }).orElseGet(() -> Stream.of(child));
                    })
                    .toList();

            return Optional.of(new Tuple2<>(state, node.withNodeList("children", children)));
        }

        return Optional.empty();
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