package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JavaToPlantUML implements Passer {
    @Override
    public Optional<Tuple2<ParseState, Node>> pass(ParseState state, Node node) {
        if (node.is("root")) {
            List<Node> newChildren = node.findNodeList("children").orElse(new ArrayList<>())
                    .stream()
                    .filter(child -> !child.is("package"))
                    .toList();

            return Optional.of(new Tuple2<>(state, node.withNodeList("children", newChildren)));
        }

        if (node.is("import")) {
            String child = node.findNodeList("segments")
                    .orElse(Collections.emptyList())
                    .getLast()
                    .findString("value")
                    .orElse("");

            Node dependency = new MapNode("dependency")
                    .withString("parent", state.location().name())
                    .withString("child", child);

            return Optional.of(new Tuple2<ParseState, Node>(state, dependency));
        }

        if(node.is("record")) {
            return Optional.of(new Tuple2<>(state, node.retype("class")));
        }

        return Optional.empty();
    }
}