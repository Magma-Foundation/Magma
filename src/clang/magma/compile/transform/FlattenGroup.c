#include "FlattenGroup.h"
expand Tuple_String_Node
expand Tuple_Node_FlattenCache
expand Tuple_String_List__Node
expand List__Node
expand Tuple_String_List__Node
expand List__Node
expand Tuple_List__Node_FlattenCache
expand List__Node
expand Tuple_List__Node_FlattenCache
expand List__Node
expand Result_Node_CompileError
expand Result_Node_CompileError
struct FlattenCache foldNodeProperty(struct FlattenCache state, struct Tuple_String_Node property}{String propertyKey = property.left();
        Node propertyValue = property.right();

        Tuple<Node, FlattenCache> tuple = flattenNode(state, propertyValue);
        Node newPropertyValue = tuple.left();
        FlattenCache newCache = tuple.right();
        return newCache.withNode(propertyKey, newPropertyValue);}struct Tuple_Node_FlattenCache flattenNode(struct FlattenCache cache, struct Node element}{if (!element.is()) return new Tuple<>(element, cache);

        Node child = element.findNode().orElse(new MapNode());
        Tuple<Node, FlattenCache> newChild = flattenNode(cache, child);

        FlattenCache groupCategories = element.streamNodeLists().foldWithInitial(newChild.right(), FlattenGroup::flattenCategory);
        return new Tuple<>(newChild.left(), groupCategories);}struct FlattenCache flattenCategory(struct FlattenCache current, struct Tuple_String_List__Node category}{return current.appendCategory(category.left(), category.right());}struct FlattenCache flattenNodeList(struct FlattenCache cache, struct Tuple_String_List__Node property}{String propertyKey = property.left();
        List_<Node> propertyValues = property.right();

        Tuple<List_<Node>, FlattenCache> listCacheTuple = propertyValues.stream().foldWithInitial(new Tuple<>(Lists.empty(), cache), FlattenGroup::flattenNodeListElement);
        return listCacheTuple.right().withNodeList(propertyKey, listCacheTuple.left());}struct Tuple_List__Node_FlattenCache flattenNodeListElement(struct Tuple_List__Node_FlattenCache tuple, struct Node node}{List_<Node> currentPropertyValues = tuple.left();
        FlattenCache currentCache = tuple.right();

        Tuple<Node, FlattenCache> flattened = flattenNode(currentCache, node);
        return new Tuple<>(currentPropertyValues.add(flattened.left()), flattened.right());}struct Node afterPass0(struct Node node}{FlattenCache cache = new FlattenCache();
        FlattenCache foldedNodes = node.streamNodes().foldWithInitial(cache, FlattenGroup::foldNodeProperty);
        FlattenCache foldedNodeLists = node.streamNodeLists().foldWithInitial(foldedNodes, FlattenGroup::flattenNodeList);
        return foldedNodeLists.tryGroup(node);}struct Result_Node_CompileError afterPass(struct State state, struct Node node}{return new Ok<>(afterPass0(node));}struct Result_Node_CompileError beforePass(struct State state, struct Node node}{return new Ok<>(node);}