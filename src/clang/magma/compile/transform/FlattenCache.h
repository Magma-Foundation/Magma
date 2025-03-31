#ifndef magma_compile_transform_FlattenCache
#define magma_compile_transform_FlattenCache
#include "../../../windows/collect/map/Maps.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/collect/map/Map_.h"
#include "../../../magma/compile/MapNode.h"
#include "../../../magma/compile/Node.h"
struct FlattenCache{
};
magma.compile.transform.public FlattenCache();
magma.compile.transform.FlattenCache withNode(String propertyKey, magma.compile.Node propertyValue);
magma.compile.transform.FlattenCache appendCategory(String category, magma.collect.list.List_<magma.compile.Node> categoryValues);
magma.compile.transform.FlattenCache withNodeList(String propertyKey, magma.collect.list.List_<magma.compile.Node> propertyValues);
magma.compile.Node tryGroup(magma.compile.Node node);
auto __lambda0__();
auto __lambda1__();
// expand magma.collect.map.Map_<String, magma.compile.Node>
// expand magma.collect.map.Map_<String, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.map.Map_<String, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
#endif

