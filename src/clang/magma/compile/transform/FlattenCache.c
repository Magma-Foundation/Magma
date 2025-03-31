#include "FlattenCache.h"
magma.compile.transform.public FlattenCache(){this(Maps.empty(), Maps.empty(), Maps.empty());
}
magma.compile.transform.FlattenCache withNode(String propertyKey, magma.compile.Node propertyValue){return FlattenCache(nodes.with(propertyKey, propertyValue), nodeLists, categories);
}
magma.compile.transform.FlattenCache appendCategory(String category, magma.collect.list.List_<magma.compile.Node> categoryValues){return FlattenCache(nodes, nodeLists, categories.ensure(category, __lambda0__.addAll(categoryValues), __lambda1__));
}
magma.compile.transform.FlattenCache withNodeList(String propertyKey, magma.collect.list.List_<magma.compile.Node> propertyValues){return FlattenCache(nodes, nodeLists.with(propertyKey, propertyValues), categories);
}
magma.compile.Node tryGroup(magma.compile.Node node){Node with = node.withNodes(nodes).withNodeLists(nodeLists);if (categories.isEmpty()) {
            return with;
        }else {
            return new MapNode("group").withNode("child", with).withNodeLists(categories);
        }
}
auto __lambda0__();
auto __lambda1__();

