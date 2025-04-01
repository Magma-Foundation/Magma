#ifndef magma_compile_transform_FlattenCache
#define magma_compile_transform_FlattenCache
#include "../../../windows/collect/map/Maps.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/collect/map/Map_.h"
#include "../../../magma/compile/MapNode.h"
#include "../../../magma/compile/Node.h"
struct FlattenCache{
};
// expand List_<Node>
// expand List_<Node>
// expand Map_<String, Node>
// expand Map_<String, List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand Map_<String, List_<Node>>
// expand List_<Node>
// expand List_<Node>
int __lambda0__();
int __lambda1__();
public FlattenCache();
FlattenCache withNode(String propertyKey, Node propertyValue);
FlattenCache appendCategory(String category, List_<Node> categoryValues);
FlattenCache withNodeList(String propertyKey, List_<Node> propertyValues);
Node tryGroup(Node node);
#endif
