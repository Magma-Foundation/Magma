package magmac.app.lang;

import magmac.api.iter.Iters;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;
import magmac.app.stage.AfterAll;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CreateDiagram implements AfterAll {
    private static List<String> findParentDependencies(String child, Map<String, List<String>> childToParents, Map<String, List<String>> dependencyMap) {
        return childToParents.getOrDefault(child, Collections.emptyList())
                .stream()
                .map(parent -> dependencyMap.getOrDefault(parent, Collections.emptyList()))
                .flatMap(Collection::stream)
                .toList();
    }

    @Override
    public Map<Location, Node> afterAll(Map<Location, Node> roots) {
        List<Node> oldRootSegments = roots.values()
                .stream()
                .map(node -> node.findNodeList("children"))
                .flatMap(Optional::stream)
                .flatMap(Collection::stream)
                .toList();

        Map<String, List<String>> childrenWithInheritedTypes = CreateDiagram.findChildrenWithInheritedTypes(oldRootSegments);
        Map<String, List<String>> childrenWithDependencies = CreateDiagram.findChildrenWithDependencies(oldRootSegments);
        List<Node> newDependencies = Iters.fromMap(childrenWithDependencies).fold(new ArrayList<Node>(), (current, entry) -> {
            String child = entry.left();
            List<String> currentDependencies = entry.right();

            List<String> parentDependencies = CreateDiagram.findParentDependencies(child, childrenWithInheritedTypes, childrenWithDependencies);
            List<String> toRemove = new ArrayList<>(parentDependencies);
            toRemove.addAll(childrenWithInheritedTypes.getOrDefault(child, new ArrayList<>()));

            List<String> list = new ArrayList<>(currentDependencies);
            list.removeAll(toRemove);

            list.forEach(parent -> {
                Node dependency = new MapNode("dependency").withString("parent", parent).withString("child", child);
                current.add(dependency);
            });
            return current;
        });

        List<Node> copy = new ArrayList<Node>();
        copy.add(new MapNode("start"));
        copy.addAll(oldRootSegments.stream()
                .filter(child -> !child.is("dependency"))
                .toList());

        copy.addAll(newDependencies);
        copy.add(new MapNode("end"));

        Node root = new MapNode().withNodeList("children", copy);
        Location location = new Location(Collections.emptyList(), "diagram");
        return Map.of(location, root);
    }

    private static Map<String, List<String>> findChildrenWithDependencies(List<Node> rootSegments) {
        return Iters.fromList(rootSegments).fold(new HashMap<String, List<String>>(), (current, node) -> {
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

    private static Map<String, List<String>> findChildrenWithInheritedTypes(List<Node> rootSegments) {
        return Iters.fromList(rootSegments).fold(new HashMap<String, List<String>>(), (current1, node1) -> {
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
}