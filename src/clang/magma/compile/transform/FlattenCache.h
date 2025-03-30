#ifndef magma_compile_transform_FlattenCache
#define magma_compile_transform_FlattenCache
#include "../../../windows/collect/map/Maps.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/collect/map/Map_.h"
#include "../../../magma/compile/MapNode.h"
#include "../../../magma/compile/Node.h"
struct FlattenCache{};
struct public FlattenCache();
struct FlattenCache withNode(struct String propertyKey, struct Node propertyValue);
struct FlattenCache appendCategory(struct String category, List_<struct Node> categoryValues);
struct FlattenCache withNodeList(struct String propertyKey, List_<struct Node> propertyValues);
struct Node tryGroup(struct Node node);
#endif
