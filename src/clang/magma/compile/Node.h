#ifndef magma_compile_Node
#define magma_compile_Node
#include "../../magma/collect/list/List_.h"
#include "../../magma/collect/map/Map_.h"
#include "../../magma/collect/stream/Stream.h"
#include "../../magma/option/Option.h"
#include "../../magma/option/Tuple.h"
struct Node{
};
magma.compile.Node withString(String propertyKey, String propertyValue);
magma.option.Option<String> findString(String propertyKey);
magma.compile.Node withNodeList(String propertyKey, magma.collect.list.List_<magma.compile.Node> propertyValues);
magma.option.Option<magma.collect.list.List_<magma.compile.Node>> findNodeList(String propertyKey);
String display();
String format(int depth);
magma.compile.Node mapNodeList(String propertyKey, magma.collect.list.List_<magma.compile.Node>(*mapper)(magma.collect.list.List_<magma.compile.Node>));
magma.compile.boolean is(String type);
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
magma.compile.boolean hasNode(String propertyKey);
magma.compile.boolean hasNodeList(String propertyKey);
// expand magma.option.Option<String>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.option.Option<magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
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
#endif

