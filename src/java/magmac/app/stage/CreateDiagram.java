package magmac.app.stage;

import magmac.api.collect.Iters;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CreateDiagram implements All {
    private static List<String> findParentDependencies(String child, Map<String, List<String>> childToParents, Map<String, List<String>> dependencyMap) {
        return childToParents.getOrDefault(child, Collections.emptyList())
                .stream()
                .map(parent -> dependencyMap.getOrDefault(parent, Collections.emptyList()))
                .flatMap(Collection::stream)
                .toList();
    }

    @Override
    public Map<Location, Node> afterAll(Map<Location, Node> roots) {
        List<Node> allChildren = roots.values()
                .stream()
                .map(node -> node.findNodeList("children"))
                .flatMap(Optional::stream)
                .flatMap(Collection::stream)
                .toList();

        Map<String, List<String>> inheritances = this.findInheritances(allChildren);
        Map<String, List<String>> dependencies = this.findDependencies(allChildren);
        List<Node> newDependencies = Iters.fromMap(dependencies).fold(new ArrayList<Node>(), (current, entry) -> {
            String parent = entry.left();
            List<String> currentDependencies = entry.right();

            List<String> parentDependencies = CreateDiagram.findParentDependencies(parent, inheritances, dependencies);
            List<String> toRemove = new ArrayList<>(parentDependencies);
            toRemove.addAll(inheritances.getOrDefault(parent, new ArrayList<>()));

            List<String> list = new ArrayList<>(currentDependencies);
            list.removeAll(toRemove);

            list.forEach(child -> current.add(new MapNode("dependency").withString("parent", parent).withString("child", child)));
            return current;
        });

        List<Node> copy = new ArrayList<Node>();
        copy.add(new MapNode("start"));
        copy.addAll(allChildren.stream()
                .filter(child -> !child.is("dependency"))
                .toList());

        copy.addAll(newDependencies);
        copy.add(new MapNode("end"));

        Node root = new MapNode().withNodeList("children", copy);
        Location location = new Location(Collections.emptyList(), "diagram");
        return Map.of(location, root);
    }

    private HashMap<String, List<String>> findDependencies(List<Node> allChildren) {
        return Iters.fromList(allChildren).fold(new HashMap<String, List<String>>(), (current, node) -> {
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

    private HashMap<String, List<String>> findInheritances(List<Node> allChildren) {
        return Iters.fromList(allChildren).fold(new HashMap<String, List<String>>(), (current1, node1) -> {
            if (node1.is("inherits")) {
                String parent1 = node1.findString("parent").orElse("");
                String child1 = node1.findString("child").orElse("");

                if (!current1.containsKey(child1)) {
                    current1.put(child1, new ArrayList<>());
                }

                current1.get(child1).add(parent1);
            }
            return current1;
        });
    }
}