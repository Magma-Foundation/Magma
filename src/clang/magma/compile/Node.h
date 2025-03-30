#ifndef magma_compile_Node
#define magma_compile_Node
#include "../../magma/collect/list/List_.h"
#include "../../magma/collect/map/Map_.h"
#include "../../magma/collect/stream/Stream.h"
#include "../../magma/option/Option.h"
#include "../../magma/option/Tuple.h"
#include "../../magma/result/Result.h"
struct Node{};
struct Node withString(struct String propertyKey, struct String propertyValue);
Option<struct String> findString(struct String propertyKey);
struct Node withNodeList(struct String propertyKey, List_<struct Node> propertyValues);
Option<List_<struct Node>> findNodeList(struct String propertyKey);
struct String display();
struct String format(struct int depth);
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
