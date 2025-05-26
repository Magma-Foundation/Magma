package magmac.app.stage;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AfterAll implements All {
    @Override
    public Map<Location, Node> afterAll(Map<Location, Node> roots) {
        List<Node> allChildren = roots.values()
                .stream()
                .map(node -> node.findNodeList("children"))
                .flatMap(Optional::stream)
                .flatMap(Collection::stream)
                .toList();

        Location location = new Location(Collections.emptyList(), "diagram");
        Node root = new MapNode().withNodeList("children", allChildren);
        return Map.of(location, root);
    }
}