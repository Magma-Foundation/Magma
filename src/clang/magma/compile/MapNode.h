#ifndef magma_compile_MapNode
#define magma_compile_MapNode
#include "../../windows/collect/map/Maps.h"
#include "../../windows/collect/stream/Streams.h"
#include "../../magma/collect/list/List_.h"
#include "../../magma/collect/map/Map_.h"
#include "../../magma/collect/stream/Joiner.h"
#include "../../magma/collect/stream/Stream.h"
#include "../../magma/option/None.h"
#include "../../magma/option/Option.h"
#include "../../magma/option/Some.h"
#include "../../magma/option/Tuple.h"
struct MapNode{Option<struct String> maybeTypeMap_<struct String, struct String> stringsMap_<struct String, struct Node> nodesMap_<struct String, List_<struct Node>> nodeLists};
struct public MapNode();
struct public MapNode(Option<struct String> maybeType, Map_<struct String, struct String> strings, Map_<struct String, struct Node> nodes, Map_<struct String, List_<struct Node>> nodeLists);
struct public MapNode(struct String type);
struct String formatEntry(struct int depth, struct String key, struct String value);
struct Node withString(struct String propertyKey, struct String propertyValue);
Option<struct String> findString(struct String propertyKey);
struct Node withNodeList(struct String propertyKey, List_<struct Node> propertyValues);
Option<List_<struct Node>> findNodeList(struct String propertyKey);
struct String display();
struct String format(struct int depth);
struct String formatList(Tuple<struct String, List_<struct Node>> entry, struct int depth);
struct Node mapNodeList(struct String propertyKey, Function<List_<struct Node>, List_<struct Node>> mapper);
int is(struct String type);
struct Node retype(struct String type);
struct Node merge(struct Node other);
Stream<Tuple<struct String, struct String>> streamStrings();
Stream<Tuple<struct String, List_<struct Node>>> streamNodeLists();
struct Node withNode(struct String propertyKey, struct Node propertyValue);
Option<struct Node> findNode(struct String propertyKey);
Stream<Tuple<struct String, struct Node>> streamNodes();
struct Node mapNode(struct String propertyKey, Function<struct Node, struct Node> mapper);
struct Node withNodeLists(Map_<struct String, List_<struct Node>> nodeLists);
struct Node withNodes(Map_<struct String, struct Node> nodes);
struct Node removeNode(struct String propertyKey);
int hasNode(struct String propertyKey);
#endif
