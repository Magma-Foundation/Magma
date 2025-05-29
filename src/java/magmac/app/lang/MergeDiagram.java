package magmac.app.lang;

import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.collect.map.Map;
import magmac.api.collect.map.Maps;
import magmac.api.iter.Iters;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.io.Location;
import magmac.app.stage.after.AfterAll;
import magmac.app.stage.unit.MapUnitSet;
import magmac.app.stage.unit.SimpleUnit;
import magmac.app.stage.unit.UnitSet;

public class MergeDiagram implements AfterAll {
    private static List<String> findParentDependencies(String child, Map<String, List<String>> childToParents, Map<String, List<String>> dependencyMap) {
        return childToParents.getOrDefault(child, Lists.empty())
                .iter()
                .map((String parent) -> dependencyMap.getOrDefault(parent, Lists.empty()))
                .flatMap(List::iter).collect(new ListCollector<>());
    }

    private static Map<String, List<String>> findChildrenWithDependencies(NodeList rootSegments) {
        return rootSegments.iter().fold(Maps.empty(), (Map<String, List<String>> current, Node node) -> {
            if (node.is("dependency")) {
                String parent = node.findString("parent").orElse("");
                String child = node.findString("child").orElse("");

                return current.mapOrPut(parent, (List<String> stringList) -> stringList.addLast(child), () -> Lists.of(child));
            }
            return current;
        });
    }

    private static Map<String, List<String>> findChildrenWithInheritedTypes(NodeList rootSegments) {
        return rootSegments.iter().fold(Maps.empty(), (Map<String, List<String>> current, Node node1) -> {
            if (!node1.is("inherits")) {
                return current;
            }

            String parent = node1.findString("parent").orElse("");
            String child = node1.findString("child").orElse("");

            return current.mapOrPut(child, (List<String> stringList) -> stringList.addLast(parent), () -> Lists.of(parent));
        });
    }

    private static MapUnitSet<Node> createInitial() {
        return new MapUnitSet<>();
    }

    @Override
    public UnitSet<Node> afterAll(UnitSet<Node> roots) {
        NodeList oldRootSegments = new InlineNodeList(roots.iterValues()
                .map((Node node) -> node.findNodeList("children"))
                .flatMap(Iters::fromOption)
                .flatMap(NodeList::iter)
                .collect(new ListCollector<>()));

        Map<String, List<String>> childrenWithInheritedTypes = MergeDiagram.findChildrenWithInheritedTypes(oldRootSegments);
        Map<String, List<String>> childrenWithDependencies = MergeDiagram.findChildrenWithDependencies(oldRootSegments);
        NodeList newDependencies = childrenWithDependencies.iter().fold(InlineNodeList.empty(), (NodeList current, Tuple2<String, List<String>> entry) -> {
            String child = entry.left();
            List<String> currentDependencies = entry.right();

            List<String> parentDependencies = MergeDiagram.findParentDependencies(child, childrenWithInheritedTypes, childrenWithDependencies);
            List<String> childWithInheritedTypes = childrenWithInheritedTypes.getOrDefault(child, Lists.empty());
            List<String> toRemove = parentDependencies.addAll(childWithInheritedTypes);
            List<String> list = currentDependencies.removeAll(toRemove);

            NodeList others = new InlineNodeList(list.iter()
                    .map((String parent) -> new MapNode("dependency").withString("parent", parent).withString("child", child))
                    .collect(new ListCollector<>()));

            return current.addAll(others);
        });

        NodeList withoutDependencies = new InlineNodeList(oldRootSegments.iter()
                .filter((Node child) -> !child.is("dependency"))
                .collect(new ListCollector<>()));

        NodeList copy = InlineNodeList.empty()
                .add(new MapNode("start"))
                .addAll(withoutDependencies)
                .addAll(newDependencies)
                .add(new MapNode("end"));

        Node node = new MapNode();
        Node root = node.withNodeList("children", copy);
        Location location = new Location(Lists.empty(), "diagram");
        return MergeDiagram.createInitial().add(new SimpleUnit<>(location, root));
    }
}