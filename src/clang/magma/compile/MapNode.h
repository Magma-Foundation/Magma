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
struct MapNode{magma.option.Option<String> maybeType;magma.collect.map.Map_<String, String> strings;magma.collect.map.Map_<String, magma.compile.Node> nodes;magma.collect.map.Map_<String, magma.collect.list.List_<magma.compile.Node>> nodeLists;
};
// expand magma.option.Option<String>
// expand magma.collect.map.Map_<String, String>
// expand magma.collect.map.Map_<String, magma.compile.Node>
// expand magma.collect.map.Map_<String, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.option.None<>
// expand magma.option.Option<String>
// expand magma.collect.map.Map_<String, String>
// expand magma.collect.map.Map_<String, magma.compile.Node>
// expand magma.collect.map.Map_<String, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.option.Some<>
// expand magma.option.Option<String>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.option.Option<magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.option.Tuple<String, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.option.Some<>
// expand magma.collect.stream.Stream<magma.option.Tuple<String, String>>
// expand magma.option.Tuple<String, String>
// expand magma.option.Tuple<String, String>
// expand magma.collect.stream.Stream<magma.option.Tuple<String, magma.collect.list.List_<magma.compile.Node>>>
// expand magma.option.Tuple<String, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.option.Tuple<String, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.option.Option<magma.compile.Node>
// expand magma.collect.stream.Stream<magma.option.Tuple<String, magma.compile.Node>>
// expand magma.option.Tuple<String, magma.compile.Node>
// expand magma.option.Tuple<String, magma.compile.Node>
// expand magma.collect.map.Map_<String, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.map.Map_<String, magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
magma.compile.public MapNode();
magma.compile.public MapNode(magma.option.Option<String> maybeType, magma.collect.map.Map_<String, String> strings, magma.collect.map.Map_<String, magma.compile.Node> nodes, magma.collect.map.Map_<String, magma.collect.list.List_<magma.compile.Node>> nodeLists);
magma.compile.public MapNode(String type);
String formatEntry(int depth, String key, String value);
magma.compile.Node withString(String propertyKey, String propertyValue);
magma.option.Option<String> findString(String propertyKey);
magma.compile.Node withNodeList(String propertyKey, magma.collect.list.List_<magma.compile.Node> propertyValues);
magma.option.Option<magma.collect.list.List_<magma.compile.Node>> findNodeList(String propertyKey);
String display();
String format(int depth);
String formatList(magma.option.Tuple<String, magma.collect.list.List_<magma.compile.Node>> entry, int depth);
magma.compile.Node mapNodeList(String propertyKey, magma.collect.list.List_<magma.compile.Node>(*mapper)(magma.collect.list.List_<magma.compile.Node>));
int is(String type);
magma.compile.Node retype(String type);
magma.compile.Node merge(magma.compile.Node other);
magma.collect.stream.Stream<magma.option.Tuple<String, String>> streamStrings();
magma.collect.stream.Stream<magma.option.Tuple<String, magma.collect.list.List_<magma.compile.Node>>> streamNodeLists();
magma.compile.Node withNode(String propertyKey, magma.compile.Node propertyValue);
magma.option.Option<magma.compile.Node> findNode(String propertyKey);
magma.collect.stream.Stream<magma.option.Tuple<String, magma.compile.Node>> streamNodes();
magma.compile.Node mapNode(String propertyKey, magma.compile.Node(*mapper)(magma.compile.Node));
magma.compile.Node withNodeLists(magma.collect.map.Map_<String, magma.collect.list.List_<magma.compile.Node>> nodeLists);
magma.compile.Node withNodes(magma.collect.map.Map_<String, magma.compile.Node> nodes);
magma.compile.Node removeNode(String propertyKey);
int hasNode(String propertyKey);
int hasNodeList(String propertyKey);
int equalsTo(magma.compile.Node other);
int doNodeListsEqual(magma.collect.list.List_<magma.compile.Node> nodeList, magma.collect.list.List_<magma.compile.Node> nodeList2);
String toString();
#endif
