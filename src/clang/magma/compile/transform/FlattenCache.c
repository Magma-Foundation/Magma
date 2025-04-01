#include "FlattenCache.h"
int __lambda0__(){return nodeList;
}
int __lambda1__(){return categoryValues;
}
public FlattenCache(){this(Maps.empty(), Maps.empty(), Maps.empty());
}
FlattenCache withNode(String propertyKey, Node propertyValue){return FlattenCache(nodes.with(propertyKey, propertyValue), nodeLists, categories);
}
FlattenCache appendCategory(String category, List_<Node> categoryValues){return FlattenCache(nodes, nodeLists, categories.ensure(category, __lambda0__.addAll(categoryValues), __lambda1__));
}
FlattenCache withNodeList(String propertyKey, List_<Node> propertyValues){return FlattenCache(nodes, nodeLists.with(propertyKey, propertyValues), categories);
}
Node tryGroup(Node node){Node with = node.withNodes(nodes).withNodeLists(nodeLists);if (categories.isEmpty()) {
            return with;
        }else {
            return new MapNode("group").withNode("child", with).withNodeLists(categories);
        }
}
