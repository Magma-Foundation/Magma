package magma.compile.transform;

import jvm.collect.map.Maps;
import magma.collect.list.List_;
import magma.collect.map.Map_;
import magma.compile.MapNode;
import magma.compile.Node;

record FlattenCache(
        Map_<String, Node> nodes,
        Map_<String, List_<Node>> nodeLists,
        Map_<String, List_<Node>> categories
) {
    public FlattenCache() {
        this(Maps.empty(), Maps.empty(), Maps.empty());
    }

    public FlattenCache withNode(String propertyKey, Node propertyValue) {
        return new FlattenCache(nodes.with(propertyKey, propertyValue), nodeLists, categories);
    }

    public FlattenCache appendCategory(String category, List_<Node> categoryValues) {
        return new FlattenCache(nodes, nodeLists, categories.ensure(category, nodeList -> nodeList.addAll(categoryValues), () -> categoryValues));
    }

    public FlattenCache withNodeList(String propertyKey, List_<Node> propertyValues) {
        return new FlattenCache(nodes, nodeLists.with(propertyKey, propertyValues), categories);
    }

    public Node tryGroup(Node node) {
        Node with = node.withNodes(nodes).withNodeLists(nodeLists);
        if (categories.isEmpty()) {
            return with;
        } else {
            return new MapNode("group").withNode("child", with).withNodeLists(categories);
        }
    }
}
