package magmac.app.lang;

import magmac.api.Tuple2;
import magmac.api.iter.collect.ListCollector;
import magmac.api.collect.map.Map;
import magmac.api.collect.map.Maps;
import magmac.api.iter.Iters;
import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.io.Location;
import magmac.app.stage.AfterAll;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MergeDiagram implements AfterAll {
    private static List<String> findParentDependencies(String child, Map<String, List<String>> childToParents, Map<String, List<String>> dependencyMap) {
        return childToParents.getOrDefault(child, Collections.emptyList())
                .stream()
                .map(parent -> dependencyMap.getOrDefault(parent, Collections.emptyList()))
                .flatMap(Collection::stream)
                .toList();
    }

    private static Map<String, List<String>> findChildrenWithDependencies(NodeList rootSegments) {
        return rootSegments.iter().fold(Maps.empty(), (current, node) -> {
            if (node.is("dependency")) {
                String parent = node.findString("parent").orElse("");
                String child = node.findString("child").orElse("");

                if (!current.containsKey(parent)) {
                    current.put(parent, new ArrayList<>());
                }

                current.get(parent).add(child);
            }
            return current;
        });
    }

    private static Map<String, List<String>> findChildrenWithInheritedTypes(NodeList rootSegments) {
        return rootSegments.iter().fold(Maps.empty(), (current1, node1) -> {
            if (!node1.is("inherits")) {
                return current1;
            }

            String parent = node1.findString("parent").orElse("");
            String child = node1.findString("child").orElse("");

            if (!current1.containsKey(child)) {
                current1.put(child, new ArrayList<>());
            }

            current1.get(child).add(parent);
            return current1;
        });
    }

    @Override
    public Map<Location, Node> afterAll(Map<Location, Node> roots) {
        NodeList oldRootSegments = new InlineNodeList(roots.iterEntries()
                .map(Tuple2::right)
                .map(node -> node.findNodeList("children"))
                .flatMap(Iters::fromOption)
                .flatMap(NodeList::iter)
                .collect(new ListCollector<>()));

        Map<String, List<String>> childrenWithInheritedTypes = MergeDiagram.findChildrenWithInheritedTypes(oldRootSegments);
        Map<String, List<String>> childrenWithDependencies = MergeDiagram.findChildrenWithDependencies(oldRootSegments);
        NodeList newDependencies = childrenWithDependencies.iterEntries().fold(InlineNodeList.empty(), (current, entry) -> {
            String child = entry.left();
            List<String> currentDependencies = entry.right();

            List<String> parentDependencies = MergeDiagram.findParentDependencies(child, childrenWithInheritedTypes, childrenWithDependencies);
            List<String> toRemove = new ArrayList<>(parentDependencies);
            toRemove.addAll(childrenWithInheritedTypes.getOrDefault(child, new ArrayList<>()));

            List<String> list = new ArrayList<>(currentDependencies);
            list.removeAll(toRemove);

            NodeList others = new InlineNodeList(Iters.fromList(list)
                    .map(parent -> new MapNode("dependency").withString("parent", parent).withString("child", child))
                    .collect(new ListCollector<>()));

            return current.addAll(others);
        });

        NodeList withoutDependencies = new InlineNodeList(oldRootSegments.iter()
                .filter(child -> !child.is("dependency"))
                .collect(new ListCollector<>()));

        NodeList copy = InlineNodeList.empty()
                .add(new MapNode("start"))
                .addAll(withoutDependencies)
                .addAll(newDependencies)
                .add(new MapNode("end"));

        Node node = new MapNode();
        Node root = node.withNodeList("children", copy);
        Location location = new Location(Collections.emptyList(), "diagram");
        return Maps.<Location, Node>empty().put(location, root);
    }
}