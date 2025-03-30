#include "FlattenCache.h"
struct public FlattenCache(){this(Maps.empty(), Maps.empty(), Maps.empty());}struct FlattenCache withNode(struct String propertyKey, struct Node propertyValue){return new FlattenCache(nodes.with(propertyKey, propertyValue), nodeLists, categories);}struct FlattenCache appendCategory(struct String category, struct List__Node categoryValues){return new FlattenCache(nodes, nodeLists, categories.ensure(category, nodeList -> nodeList.addAll(categoryValues), () -> categoryValues));}struct FlattenCache withNodeList(struct String propertyKey, struct List__Node propertyValues){return new FlattenCache(nodes, nodeLists.with(propertyKey, propertyValues), categories);}struct Node tryGroup(struct Node node){Node with = node.withNodes(nodes).withNodeLists(nodeLists);if (categories.isEmpty()) {
            return with;
        }else {
            return new MapNode().withNode(, with).withNodeLists(categories);
        }}