#include "FlattenCache.h"
struct public FlattenCache(){this(Maps.empty(), Maps.empty(), Maps.empty());
}
struct FlattenCache withNode(struct String propertyKey, struct Node propertyValue){return FlattenCache(nodes.with(propertyKey, propertyValue), nodeLists, categories);
}
struct FlattenCache appendCategory(struct String category, struct List__Node categoryValues){return FlattenCache(nodes, nodeLists, categories.ensure(category, __lambda0__, __lambda1__));
}
struct FlattenCache withNodeList(struct String propertyKey, struct List__Node propertyValues){return FlattenCache(nodes, nodeLists.with(propertyKey, propertyValues), categories);
}
struct Node tryGroup(struct Node node){Node with = node.withNodes(nodes).withNodeLists(nodeLists);if (categories.isEmpty()) {
            return with;
        }else {
            return new MapNode("group").withNode("child", with).withNodeLists(categories);
        }
}
auto __lambda0__();
auto __lambda1__();

