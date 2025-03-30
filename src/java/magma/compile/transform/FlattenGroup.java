package magma.compile.transform;

import jvm.collect.list.Lists;
import jvm.collect.map.Maps;
import magma.collect.list.List_;
import magma.collect.map.Map_;
import magma.compile.CompileError;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.option.Tuple;
import magma.result.Ok;
import magma.result.Result;

public class FlattenGroup implements Transformer {
    private static Cache foldNodeProperty(Cache state, Tuple<String, Node> property) {
        String propertyKey = property.left();
        Node propertyValue = property.right();

        Tuple<Node, Cache> tuple = flattenNode(state, propertyValue);
        Node newPropertyValue = tuple.left();
        Cache newCache = tuple.right();
        return newCache.withNode(propertyKey, newPropertyValue);
    }

    private static Tuple<Node, Cache> flattenNode(Cache cache, Node element) {
        if (!element.is("group")) return new Tuple<>(element, cache);

        Node child = element.findNode("child").orElse(new MapNode());
        Tuple<Node, Cache> newChild = flattenNode(cache, child);

        Cache groupCategories = element.streamNodeLists().foldWithInitial(newChild.right(), FlattenGroup::flattenCategory);
        return new Tuple<>(newChild.left(), groupCategories);
    }

    private static Cache flattenCategory(Cache current, Tuple<String, List_<Node>> category) {
        return current.appendCategory(category.left(), category.right());
    }

    private static Cache flattenNodeList(Cache cache, Tuple<String, List_<Node>> property) {
        String propertyKey = property.left();
        List_<Node> propertyValues = property.right();

        Tuple<List_<Node>, Cache> listCacheTuple = propertyValues.stream().foldWithInitial(new Tuple<>(Lists.empty(), cache), FlattenGroup::flattenNodeListElement);
        return listCacheTuple.right().withNodeList(propertyKey, listCacheTuple.left());
    }

    private static Tuple<List_<Node>, Cache> flattenNodeListElement(Tuple<List_<Node>, Cache> tuple, Node node) {
        List_<Node> currentPropertyValues = tuple.left();
        Cache currentCache = tuple.right();

        Tuple<Node, Cache> flattened = flattenNode(currentCache, node);
        return new Tuple<>(currentPropertyValues.add(flattened.left()), flattened.right());
    }

    private Node afterPass0(Node node) {
        Cache cache = new Cache();
        Cache foldedNodes = node.streamNodes().foldWithInitial(cache, FlattenGroup::foldNodeProperty);
        Cache foldedNodeLists = node.streamNodeLists().foldWithInitial(foldedNodes, FlattenGroup::flattenNodeList);
        return foldedNodeLists.tryGroup(node);
    }

    @Override
    public Result<Node, CompileError> afterPass(Node node) {
        return new Ok<>(afterPass0(node));
    }

    record Cache(
            Map_<String, Node> nodes,
            Map_<String, List_<Node>> nodeLists,
            Map_<String, List_<Node>> categories
    ) {
        public Cache() {
            this(Maps.empty(), Maps.empty(), Maps.empty());
        }

        public Cache withNode(String propertyKey, Node propertyValue) {
            return new Cache(nodes.with(propertyKey, propertyValue), nodeLists, categories);
        }

        public Cache appendCategory(String category, List_<Node> categoryValues) {
            return new Cache(nodes, nodeLists, categories.ensure(category, nodeList -> nodeList.addAll(categoryValues), () -> categoryValues));
        }

        public Cache withNodeList(String propertyKey, List_<Node> propertyValues) {
            return new Cache(nodes, nodeLists.with(propertyKey, propertyValues), categories);
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
}
