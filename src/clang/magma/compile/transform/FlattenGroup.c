#include "FlattenGroup.h"
int __lambda0__(){return (state, value);
}
int __lambda1__(){return (state, value);
}
FlattenCache foldNodeProperty(FlattenCache state, Tuple<String, Node> property){String propertyKey = property.left();
        Node propertyValue = property.right();

        Tuple<Node, FlattenCache> tuple = flattenNode(state, propertyValue);
        Node newPropertyValue = tuple.left();
        FlattenCache newCache = tuple.right();return newCache.withNode(propertyKey, newPropertyValue);
}
Tuple<Node, FlattenCache> flattenNode(FlattenCache cache, Node element){if (!element.is("group")) return new Tuple<>(element, cache);

        Node child = element.findNode("child").orElse(new MapNode());
        Tuple<Node, FlattenCache> newChild = flattenNode(cache, child);

        FlattenCache groupCategories = element.streamNodeLists().foldWithInitial(newChild.right(), FlattenGroup::flattenCategory);return (newChild.left(), groupCategories);
}
FlattenCache flattenCategory(FlattenCache current, Tuple<String, List_<Node>> category){return current.appendCategory(category.left(), category.right());
}
FlattenCache flattenNodeList(FlattenCache cache, Tuple<String, List_<Node>> property){String propertyKey = property.left();
        List_<Node> propertyValues = property.right();

        Tuple<List_<Node>, FlattenCache> listCacheTuple = propertyValues.stream().foldWithInitial(new Tuple<>(Lists.empty(), cache), FlattenGroup::flattenNodeListElement);return listCacheTuple.right().withNodeList(propertyKey, listCacheTuple.left());
}
Tuple<List_<Node>, FlattenCache> flattenNodeListElement(Tuple<List_<Node>, FlattenCache> tuple, Node node){List_<Node> currentPropertyValues = tuple.left();
        FlattenCache currentCache = tuple.right();

        Tuple<Node, FlattenCache> flattened = flattenNode(currentCache, node);return (currentPropertyValues.add(flattened.left()), flattened.right());
}
Node afterPass0(Node node){FlattenCache cache = new FlattenCache();
        FlattenCache foldedNodes = node.streamNodes().foldWithInitial(cache, FlattenGroup::foldNodeProperty);
        FlattenCache foldedNodeLists = node.streamNodeLists().foldWithInitial(foldedNodes, FlattenGroup::flattenNodeList);return foldedNodeLists.tryGroup(node);
}
Result<Node, CompileError> afterPass0(State state, Node node){return (afterPass0(node));
}
Result<Node, CompileError> beforePass0(State state, Node node){return (node);
}
Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node){return beforePass0(state, node).mapValue(__lambda0__);
}
Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node){return afterPass0(state, node).mapValue(__lambda1__);
}
