package magma.compile.transform;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.option.Tuple;
import magma.result.Ok;
import magma.result.Result;

public class FlattenGroup implements Transformer {
    private static FlattenCache foldNodeProperty(FlattenCache state, Tuple<String, Node> property) {
        String propertyKey = property.left();
        Node propertyValue = property.right();

        Tuple<Node, FlattenCache> tuple = flattenNode(state, propertyValue);
        Node newPropertyValue = tuple.left();
        FlattenCache newCache = tuple.right();
        return newCache.withNode(propertyKey, newPropertyValue);
    }

    private static Tuple<Node, FlattenCache> flattenNode(FlattenCache cache, Node element) {
        if (!element.is("group")) return new Tuple<>(element, cache);

        Node child = element.findNode("child").orElse(new MapNode());
        Tuple<Node, FlattenCache> newChild = flattenNode(cache, child);

        FlattenCache groupCategories = element.streamNodeLists().foldWithInitial(newChild.right(), FlattenGroup::flattenCategory);
        return new Tuple<>(newChild.left(), groupCategories);
    }

    private static FlattenCache flattenCategory(FlattenCache current, Tuple<String, List_<Node>> category) {
        return current.appendCategory(category.left(), category.right());
    }

    private static FlattenCache flattenNodeList(FlattenCache cache, Tuple<String, List_<Node>> property) {
        String propertyKey = property.left();
        List_<Node> propertyValues = property.right();

        Tuple<List_<Node>, FlattenCache> listCacheTuple = propertyValues.stream().foldWithInitial(new Tuple<>(Lists.empty(), cache), FlattenGroup::flattenNodeListElement);
        return listCacheTuple.right().withNodeList(propertyKey, listCacheTuple.left());
    }

    private static Tuple<List_<Node>, FlattenCache> flattenNodeListElement(Tuple<List_<Node>, FlattenCache> tuple, Node node) {
        List_<Node> currentPropertyValues = tuple.left();
        FlattenCache currentCache = tuple.right();

        Tuple<Node, FlattenCache> flattened = flattenNode(currentCache, node);
        return new Tuple<>(currentPropertyValues.add(flattened.left()), flattened.right());
    }

    private Node afterPass0(Node node) {
        FlattenCache cache = new FlattenCache();
        FlattenCache foldedNodes = node.streamNodes().foldWithInitial(cache, FlattenGroup::foldNodeProperty);
        FlattenCache foldedNodeLists = node.streamNodeLists().foldWithInitial(foldedNodes, FlattenGroup::flattenNodeList);
        return foldedNodeLists.tryGroup(node);
    }

    @Override
    public Result<Node, CompileError> afterPass(State state, Node node) {
        return new Ok<>(afterPass0(node));
    }
}
