#ifndef magma_compile_transform_FlattenCache
#define magma_compile_transform_FlattenCache
#include "../../../windows/collect/map/Maps.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/collect/map/Map_.h"
#include "../../../magma/compile/MapNode.h"
#include "../../../magma/compile/Node.h"
struct FlattenCache{};
// expand List__Node = List_<struct Node>
// expand List__Node = List_<struct Node>
// expand Map__String_Node = Map_<struct String, struct Node>
// expand Map__String_List__Node = Map_<struct String, struct List__Node>
// expand List__Node = List_<struct Node>
// expand Map__String_List__Node = Map_<struct String, struct List__Node>
// expand List__Node = List_<struct Node>
struct public FlattenCache();
struct FlattenCache withNode(struct String propertyKey, struct Node propertyValue);
struct FlattenCache appendCategory(struct String category, struct List__Node categoryValues);
struct FlattenCache withNodeList(struct String propertyKey, struct List__Node propertyValues);
struct Node tryGroup(struct Node node);
#endif
