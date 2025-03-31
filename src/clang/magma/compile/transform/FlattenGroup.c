#include "FlattenGroup.h"
auto __lambda0__();
auto __lambda1__();
magma.compile.transform.FlattenCache foldNodeProperty(magma.compile.transform.FlattenCache state, magma.option.Tuple<String, magma.compile.Node> property){String propertyKey = property.left();
        Node propertyValue = property.right();

        Tuple<Node, FlattenCache> tuple = flattenNode(state, propertyValue);
        Node newPropertyValue = tuple.left();
        FlattenCache newCache = tuple.right();return newCache.withNode(propertyKey, newPropertyValue);
}
magma.option.Tuple<magma.compile.Node, magma.compile.transform.FlattenCache> flattenNode(magma.compile.transform.FlattenCache cache, magma.compile.Node element){if (!element.is("group")) return new Tuple<>(element, cache);

        Node child = element.findNode("child").orElse(new MapNode());
        Tuple<Node, FlattenCache> newChild = flattenNode(cache, child);

        FlattenCache groupCategories = element.streamNodeLists().foldWithInitial(newChild.right(), FlattenGroup::flattenCategory);return (newChild.left(), groupCategories);
}
magma.compile.transform.FlattenCache flattenCategory(magma.compile.transform.FlattenCache current, magma.option.Tuple<String, magma.collect.list.List_<magma.compile.Node>> category){return current.appendCategory(category.left(), category.right());
}
magma.compile.transform.FlattenCache flattenNodeList(magma.compile.transform.FlattenCache cache, magma.option.Tuple<String, magma.collect.list.List_<magma.compile.Node>> property){String propertyKey = property.left();
        List_<Node> propertyValues = property.right();

        Tuple<List_<Node>, FlattenCache> listCacheTuple = propertyValues.stream().foldWithInitial(new Tuple<>(Lists.empty(), cache), FlattenGroup::flattenNodeListElement);return listCacheTuple.right().withNodeList(propertyKey, listCacheTuple.left());
}
magma.option.Tuple<magma.collect.list.List_<magma.compile.Node>, magma.compile.transform.FlattenCache> flattenNodeListElement(magma.option.Tuple<magma.collect.list.List_<magma.compile.Node>, magma.compile.transform.FlattenCache> tuple, magma.compile.Node node){List_<Node> currentPropertyValues = tuple.left();
        FlattenCache currentCache = tuple.right();

        Tuple<Node, FlattenCache> flattened = flattenNode(currentCache, node);return (currentPropertyValues.add(flattened.left()), flattened.right());
}
magma.compile.Node afterPass0(magma.compile.Node node){FlattenCache cache = new FlattenCache();
        FlattenCache foldedNodes = node.streamNodes().foldWithInitial(cache, FlattenGroup::foldNodeProperty);
        FlattenCache foldedNodeLists = node.streamNodeLists().foldWithInitial(foldedNodes, FlattenGroup::flattenNodeList);return foldedNodeLists.tryGroup(node);
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> afterPass0(magma.compile.transform.State state, magma.compile.Node node){return (afterPass0(node));
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> beforePass0(magma.compile.transform.State state, magma.compile.Node node){return (node);
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> beforePass(magma.compile.transform.State state, magma.compile.Node node){return beforePass0(state, node).mapValue(__lambda0__);
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> afterPass(magma.compile.transform.State state, magma.compile.Node node){return afterPass0(state, node).mapValue(__lambda1__);
}
