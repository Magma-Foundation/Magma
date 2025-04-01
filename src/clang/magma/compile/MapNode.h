#ifndef magma_compile_MapNode
#define magma_compile_MapNode
#include "../../windows/collect/list/Lists.h"
#include "../../windows/collect/map/Maps.h"
#include "../../windows/collect/stream/Streams.h"
#include "../../magma/collect/list/List_.h"
#include "../../magma/collect/map/MapCollector.h"
#include "../../magma/collect/map/Map_.h"
#include "../../magma/collect/stream/Joiner.h"
#include "../../magma/collect/stream/Stream.h"
#include "../../magma/option/None.h"
#include "../../magma/option/Option.h"
#include "../../magma/option/Some.h"
#include "../../magma/option/Tuple.h"
struct MapNode{Option<String> maybeType;Map_<String, String> strings;Map_<String, Node> nodes;Map_<String, List_<Node>> nodeLists;
};
// expand Option<String>
// expand Map_<String, String>
// expand Map_<String, Node>
// expand Map_<String, List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand None<>
// expand Option<String>
// expand Map_<String, String>
// expand Map_<String, Node>
// expand Map_<String, List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand Some<>
// expand Option<String>
// expand List_<Node>
// expand Option<List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand Tuple<String, List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand List_<Node>
// expand List_<Node>
// expand Some<>
// expand Stream<Tuple<String, String>>
// expand Tuple<String, String>
// expand Tuple<String, String>
// expand Stream<Tuple<String, List_<Node>>>
// expand Tuple<String, List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand Tuple<String, List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand Option<Node>
// expand Stream<Tuple<String, Node>>
// expand Tuple<String, Node>
// expand Tuple<String, Node>
// expand Map_<String, List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand Map_<String, Node>
// expand List_<Node>
// expand List_<Node>
int __lambda0__();
int __lambda1__();
int __lambda2__();
int __lambda3__();
public MapNode();
public MapNode(Option<String> maybeType, Map_<String, String> strings, Map_<String, Node> nodes, Map_<String, List_<Node>> nodeLists);
public MapNode(String type);
String formatEntry(int depth, String key, String value);
Node withString(String propertyKey, String propertyValue);
Option<String> findString(String propertyKey);
Node withNodeList(String propertyKey, List_<Node> propertyValues);
Option<List_<Node>> findNodeList(String propertyKey);
String display();
String format(int depth);
String formatList(Tuple<String, List_<Node>> entry, int depth);
Node mapNodeList(String propertyKey, List_<Node>(*mapper)(List_<Node>));
int is(String type);
Node retype(String type);
Node merge(Node other);
Stream<Tuple<String, String>> streamStrings();
Stream<Tuple<String, List_<Node>>> streamNodeLists();
Node withNode(String propertyKey, Node propertyValue);
Option<Node> findNode(String propertyKey);
Stream<Tuple<String, Node>> streamNodes();
Node mapNode(String propertyKey, Node(*mapper)(Node));
Node withNodeLists(Map_<String, List_<Node>> nodeLists);
Node withNodes(Map_<String, Node> nodes);
Node removeNode(String propertyKey);
int hasNode(String propertyKey);
int hasNodeList(String propertyKey);
int equalsTo(Node other);
int hasNoType();
int doNodeListsEqual(List_<Node> nodeList, List_<Node> nodeList2);
String toString();
#endif
