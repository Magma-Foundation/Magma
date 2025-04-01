#ifndef magma_compile_Node
#define magma_compile_Node
#include "../../magma/collect/list/List_.h"
#include "../../magma/collect/map/Map_.h"
#include "../../magma/collect/stream/Stream.h"
#include "../../magma/option/Option.h"
#include "../../magma/option/Tuple.h"
struct Node{
};
// expand Option<String>
// expand List_<Node>
// expand Option<List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand List_<Node>
// expand List_<Node>
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
Node withString(String propertyKey, String propertyValue);
Option<String> findString(String propertyKey);
Node withNodeList(String propertyKey, List_<Node> propertyValues);
Option<List_<Node>> findNodeList(String propertyKey);
String display();
String format(int depth);
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
#endif
