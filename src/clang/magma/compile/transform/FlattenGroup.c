#include "FlattenGroup.h"
struct FlattenCache foldNodeProperty(struct FlattenCache state, Tuple<struct String, struct Node> property}{String propertyKey = property.left();
        Node propertyValue = property.right();

        Tuple<Node, FlattenCache> tuple = flattenNode(state, propertyValue);
        Node newPropertyValue = tuple.left();
        FlattenCache newCache = tuple.right();
        return newCache.withNode(propertyKey, newPropertyValue);}Tuple<struct Node, struct FlattenCache> flattenNode(struct FlattenCache cache, struct Node element}{if (!element.is()) return new Tuple<>(element, cache);

        Node child = element.findNode().orElse(new MapNode());
        Tuple<Node, FlattenCache> newChild = flattenNode(cache, child);

        FlattenCache groupCategories = element.streamNodeLists().foldWithInitial(newChild.right(), FlattenGroup::flattenCategory);
        return new Tuple<>(newChild.left(), groupCategories);}struct FlattenCache flattenCategory(struct FlattenCache current, Tuple<struct String, List_<struct Node>> category}{return current.appendCategory(category.left(), category.right());}struct FlattenCache flattenNodeList(struct FlattenCache cache, Tuple<struct String, List_<struct Node>> property}{String propertyKey = property.left();
        List_<Node> propertyValues = property.right();

        Tuple<List_<Node>, FlattenCache> listCacheTuple = propertyValues.stream().foldWithInitial(new Tuple<>(Lists.empty(), cache), FlattenGroup::flattenNodeListElement);
        return listCacheTuple.right().withNodeList(propertyKey, listCacheTuple.left());}Tuple<List_<struct Node>, struct FlattenCache> flattenNodeListElement(Tuple<List_<struct Node>, struct FlattenCache> tuple, struct Node node}{List_<Node> currentPropertyValues = tuple.left();
        FlattenCache currentCache = tuple.right();

        Tuple<Node, FlattenCache> flattened = flattenNode(currentCache, node);
        return new Tuple<>(currentPropertyValues.add(flattened.left()), flattened.right());}struct Node afterPass0(struct Node node}{FlattenCache cache = new FlattenCache();
        FlattenCache foldedNodes = node.streamNodes().foldWithInitial(cache, FlattenGroup::foldNodeProperty);
        FlattenCache foldedNodeLists = node.streamNodeLists().foldWithInitial(foldedNodes, FlattenGroup::flattenNodeList);
        return foldedNodeLists.tryGroup(node);}Result<struct Node, struct CompileError> afterPass(struct State state, struct Node node}{return new Ok<>(afterPass0(node));}Result<struct Node, struct CompileError> beforePass(struct State state, struct Node node}{return new Ok<>(node);}